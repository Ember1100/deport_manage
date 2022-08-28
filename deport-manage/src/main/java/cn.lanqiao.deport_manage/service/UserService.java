package cn.lanqiao.deport_manage.service;

import cn.lanqiao.deport_manage.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> getAllUser();
    public int isLogin(Map<String,String> map);

    public User searchUser(String username);

    public PageInfo<User> getUserPage(int pageNo,int pageSize);
}
