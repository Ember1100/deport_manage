package cn.lanqiao.deport_manage.web;

import cn.lanqiao.deport_manage.entity.User;
import cn.lanqiao.deport_manage.service.UserService;
import cn.lanqiao.deport_manage.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping
public class UserController {
    /*注入业务层*/
    @Autowired
    private UserService userService;

    //获取所有用户接口
    @RequestMapping("user")
    @ResponseBody
    public Result getAllUser() {
        try {
            List<User> list = userService.getAllUser();
            return Result.success(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }

    //管理员或用户登录接口
    @PostMapping("/login")
    public Result login(String username, String password) {
        try {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", username);
            hashMap.put("password", password);
            int ok = userService.isLogin(hashMap);
            if (ok == 0) {
                return Result.fail(ok, "密码错误");
            } else if (ok==1){
                return Result.success(ok, "登录成功");
            } else {
              return  Result.fail(ok,"用户名错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }

    //查找用户接口
    @RequestMapping("searchuser")
    public Result search(String username) {
       try {
           User user = userService.searchUser(username);
           if(user==null)
           {
             return Result.fail("用户不存在");
           }
           else{
               return Result.success(user);
           }
       }catch (Exception e) {
           e.printStackTrace();
       }
        return Result.fail("服务器内部错误");
    }

}
