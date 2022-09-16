package com.wl.springboot.config;

//import com.mysql.jdbc.MySQLConnection;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.jdbc.datasource.ConnectionProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据源读写分离路由
 *
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}) })
public class RWPlugin implements Interceptor ,PriorityOrdered {

    public Object intercept(Invocation invocation) throws Throwable {

        Connection conn = (Connection) invocation.getArgs()[0];
        conn = unwrapConnection(conn);
            //强制走写库
            /*if(ConnectionHold.FORCE_WRITE.get() != null && ConnectionHold.FORCE_WRITE.get()){
                routeConnection(ConnectionHold.WRITE, conn);
                return invocation.proceed();
            }*/
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaObject = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
            MappedStatement mappedStatement;
            if (statementHandler instanceof RoutingStatementHandler) {
                mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            } else {
                mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
            }
            String key = DataSourceType.write.getType();

            if (mappedStatement.getSqlCommandType() == SqlCommandType.SELECT && !mappedStatement.getId().endsWith("!selectKey")) {
                key = DataSourceType.read.getType();
            }
            routeConnection(key, conn);

        return invocation.proceed();

    }

    private void routeConnection(String key, Connection conn) {
        if(DataSourceType.read.getType().equals(key)){
//            ConnectionProxy conToUse = (ConnectionProxy) conn;
//            conn = conToUse.getTargetConnection();
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DataSourceContextHolder.setRead();
        }
    }

    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties properties) {
        // NOOP

    }
    /**
     * MyBatis wraps the JDBC Connection with a logging proxy but Spring registers the original connection so it should
     * be unwrapped before calling {@code DataSourceUtils.isConnectionTransactional(Connection, DataSource)}
     *
     * @param connection May be a {@code ConnectionLogger} proxy
     * @return the original JDBC {@code Connection}
     */
    private Connection unwrapConnection(Connection connection) {
        if (Proxy.isProxyClass(connection.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(connection);
            if (handler instanceof ConnectionLogger) {
                return ((ConnectionLogger) handler).getConnection();
            }
        }
        return connection;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 11;
    }
}
