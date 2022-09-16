package com.wl.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public void put(String key, Object value, long millis) {
        redisTemplate.opsForValue().set(key, value, millis, TimeUnit.MINUTES);
    }

    public void putForHash(String objectKey, String hkey, String value) {
        redisTemplate.opsForHash().put(objectKey, hkey, value);
    }

    //对指定key的键值减一
    public Long decrBy(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

}

