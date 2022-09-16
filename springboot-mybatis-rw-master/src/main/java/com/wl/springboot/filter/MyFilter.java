package com.wl.springboot.filter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Slf4j
@Order(1)
@WebFilter(filterName = "myFilter", urlPatterns = {"/*"})
public class MyFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("请求处理");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("MyFilter, URL：{}", request.getRequestURI());
        if (request.getRequestURI().contains("sec")) {
            try {
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info("非法URL：{}", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            PrintWriter writer = response.getWriter();
            writer.print("Sorry your request path is wrong!!!!!!! no access");
        }
    }

    @Override
    public void destroy() {
        log.info("过滤器销毁");
    }
}