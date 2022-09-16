package com.wl.springboot.dao;

import com.wl.springboot.domain.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface StockMapper {

    List<Stock> selectList(@Param("name") String name);

    Integer updateByPrimaryKey(Stock stock);

}

