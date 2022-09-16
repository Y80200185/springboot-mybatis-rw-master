package com.wl.springboot.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


@Data
public class Stock implements Serializable {
    private static final long serialVersionUID = 6235666939721331057L;
    Integer id;
    String name;
    Integer stock;
}

