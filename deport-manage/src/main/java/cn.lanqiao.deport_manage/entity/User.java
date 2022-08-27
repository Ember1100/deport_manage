package cn.lanqiao.deport_manage.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer id;

    private String username;

    private String nickname;

    private String password;

    private String sex;

    private String phone;

    private String email;

    private String address;

    private Integer type;


}