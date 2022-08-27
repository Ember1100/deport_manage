package cn.lanqiao.deport_manage.service.impl;

import cn.lanqiao.deport_manage.entity.User;
import cn.lanqiao.deport_manage.mapper.UserMapper;
import cn.lanqiao.deport_manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        List<User> list =null;
        list = userMapper.getAllUser();
        return list;
    }

    @Override
    public int isLogin(Map<String,String> map) {
        /*调用dao层*/
        User user =userMapper.getUserWithName(map.get("username"));
        System.out.println(user);
        if(user!=null)
        {
            if(user.getPassword().equals(map.get("password")))
                return 1;
            return 0;
        }
        return 2;
    }


    @Override
    public User searchUser(String username) {
        return userMapper.getUserWithName(username);
    }
}

