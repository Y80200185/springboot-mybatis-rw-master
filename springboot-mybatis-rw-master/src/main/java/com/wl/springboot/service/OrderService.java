package com.wl.springboot.service;

import com.wl.springboot.annotation.WriteDataSource;
import com.wl.springboot.config.MyRabbitMQConfig;
import com.wl.springboot.dao.OrderMapper;
import com.wl.springboot.domain.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderService{

    @Autowired
    private OrderMapper orderMapper;
    @WriteDataSource
    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,readOnly=false)
    public void createOrder(Order order) {
        orderMapper.insert(order);
    }

}

