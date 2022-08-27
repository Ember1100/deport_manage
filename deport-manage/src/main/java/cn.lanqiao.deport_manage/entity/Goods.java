package cn.lanqiao.deport_manage.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Goods implements Serializable {
    private Integer id;

    private String goodsname;

    private String context;

    private Integer number;

    private Integer max;

    private Integer min;

    private Integer price;

    private String username;


}