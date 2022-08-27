package cn.lanqiao.deport_manage.service;

import cn.lanqiao.deport_manage.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> getAllUser();
    public int isLogin(Map<String,String> map);

    public User searchUser(String username);
}
