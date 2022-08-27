package cn.lanqiao.deport_manage.mapper;

import cn.lanqiao.deport_manage.entity.User;

import java.util.List;

public interface UserMapper {

    //查询所有用户信息
    public List<User> getAllUser();

    public User getUserWithName(String username);
}