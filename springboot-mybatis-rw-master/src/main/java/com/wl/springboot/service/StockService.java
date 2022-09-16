package com.wl.springboot.service;

import com.wl.springboot.annotation.WriteDataSource;
import com.wl.springboot.config.MyRabbitMQConfig;
import com.wl.springboot.dao.StockMapper;
import com.wl.springboot.domain.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
@Slf4j
public class StockService {

    @Autowired
    private StockMapper stockMapper;
    @WriteDataSource
//    @Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT,readOnly=false)
    public void decrByStock(String stockName) {
        List<Stock> stocks = stockMapper.selectList(stockName);
        if (!CollectionUtils.isEmpty(stocks)) {
            Stock stock = stocks.get(0);
            stock.setStock(stock.getStock() - 1);
            stockMapper.updateByPrimaryKey(stock);
        }
    }


//    @WriteDataSource
//    @Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT,readOnly=false)
    public Integer selectByName(String stockName) {
        List<Stock> stocks = stockMapper.selectList(stockName);
        if (!CollectionUtils.isEmpty(stocks)) {
            return stocks.get(0).getStock().intValue();
        }
        return 0;
    }

}


