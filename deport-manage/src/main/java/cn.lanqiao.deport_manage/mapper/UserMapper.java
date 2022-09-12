package cn.lanqiao.deport_manage.mapper;


import cn.lanqiao.deport_manage.entity.User;


import java.util.List;

public interface UserMapper {

    //查询所有用户信息
     List<User> getAllUser();

   User getUserWithName(String username);
    //更新用户
     int updateUser(User user);
    int registerUser(User user);

    //   新增用户
     int addUser(User user);

    int deleteUser(int id);
}
