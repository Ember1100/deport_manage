package cn.lanqiao.deport_manage.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Record implements Serializable {
    private int id;
    private String goodsName;
    private String username;
    private int number;
    private String type;
    private String state;

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", username='" + username + '\'' +
                ", number=" + number +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                '}';
    }


}