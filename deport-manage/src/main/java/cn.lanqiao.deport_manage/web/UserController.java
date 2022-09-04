package cn.lanqiao.deport_manage.web;

import cn.lanqiao.deport_manage.entity.User;
import cn.lanqiao.deport_manage.service.UserService;
import cn.lanqiao.deport_manage.utils.Result;
import com.github.pagehelper.PageInfo;
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
    @RequestMapping("/user")
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

    //分页接口
    @PostMapping("/getUserPage")
    public Result getUserPage(@RequestParam(defaultValue = "1") int pageNo,
                              @RequestParam(defaultValue = "5") int pageSize) {
        try {
            PageInfo<User> page = userService.getUserPage(pageNo, pageSize);
            return Result.success(page);
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
            } else if (ok == 1) {
                return Result.success(ok, "登录成功");
            } else {
                return Result.fail(ok, "用户名错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }

    //查找用户接口
    @RequestMapping("/searchUser")
    public Result search(String username) {
        try {
            User user = userService.searchUser(username);
            if (user == null) {
                return Result.fail("用户不存在");
            } else {
                return Result.success(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }

    @PostMapping("/saveUser")
    public Result saveUser(User user) {
        try {
            int i = userService.updateUser(user);
            if (i == 1) {
                return Result.success(1, "操作成功");
            } else {
                return Result.success(0, "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }


    //    增加用户
    @PostMapping("/adduser")
    public Result addUser(User user) {
        try {

            user.setType(0);

            int i = userService.addUser(user);
            System.out.println(user);
            if (i == 1) {
                return Result.success(1, "新增成功");
            } else {
                return Result.success(0, "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }

    //更新
    @PostMapping("/updateuser")
    public Result updateUser(User user) {
        try {


            int i = userService.updateUser(user);
            System.out.println(user);
            if (i == 1) {
                return Result.success(1, "更新成功");
            } else {
                return Result.success(0, "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }


    @PostMapping("/registerUser")
    public Result registerUser(User user) {
        try {
            int i = userService.registerUser(user);
            if (i == 0) {
                return Result.fail(0,"注册失败");
            } else {
                return Result.success(1,"注册成功");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }

    @RequestMapping("/deleteUser")
    public Result deleteUser(int id) {
        try {
            int i = userService.del(id);
            if (i == 1) {
                return Result.success(1, "物品取出成功");
            } else {
                return Result.fail("物品取出失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }
}



