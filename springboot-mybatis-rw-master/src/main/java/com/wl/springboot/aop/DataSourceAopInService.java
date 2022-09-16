package com.wl.springboot.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import com.wl.springboot.config.DataSourceContextHolder;


@Aspect
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
@Component
public class DataSourceAopInService implements PriorityOrdered{

private static Logger log = LoggerFactory.getLogger(DataSourceAopInService.class);

	/*使用ReadDataSource注解时，提示数据库切换到读库*/
	@Before("@annotation(com.wl.springboot.annotation.ReadDataSource) ")
	public void setReadDataSourceType() {

			DataSourceContextHolder.setRead();

	}

	/*使用ReadDataSource注解时，提示数据库切换到写库*/
	@Before("@annotation(com.wl.springboot.annotation.WriteDataSource) ")
	public void setWriteDataSourceType() {
		DataSourceContextHolder.setWrite();
	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE + 10;
	}

}
