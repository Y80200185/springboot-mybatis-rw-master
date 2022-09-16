package com.wl.springboot.dao;

import com.wl.springboot.domain.Order;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OrderMapper {

    Integer insert(Order order);
}

