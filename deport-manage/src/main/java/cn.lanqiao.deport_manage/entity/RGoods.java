package cn.lanqiao.deport_manage.entity;

import lombok.Data;

@Data
public class RGoods {
    private Integer aid;

    private String goodsName;

    private String context;

    private Integer number;

    private Integer max;

    private Integer min;

    private Integer price;

    private String username;
}
