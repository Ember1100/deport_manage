package cn.lanqiao.deport_manage.web;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.service.GoodsService;
import cn.lanqiao.deport_manage.utils.Result;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class GoodsController {
    /*注入业务层*/
    @Autowired
    private GoodsService goodsService;

    //获取所有商品接口
    @RequestMapping("goods")
    @ResponseBody
    public Result getAllGoods(){
       try{
           List<Goods> list =goodsService.getAllGoods();
           return Result.success(list);
       }catch (Exception e) {
           e.printStackTrace();
       }
       return Result.fail("服务器出错");
    }

    @RequestMapping("/goodsGetPage")
    public Result getPage(@RequestParam(defaultValue = "1") int pageNo,
                          @RequestParam(defaultValue = "5") int pageSize) {
        try {
            PageInfo<Goods> page = goodsService.getPage(pageNo,pageSize);
            return  Result.success(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }

    //查看用户物品详情
    @PostMapping("/searchGoodsByUser")
    public Result searchGoodsByUser(String username) {
        try {
            List<Goods> goods = goodsService.getGoodsWithUsername(username);
            return Result.success(goods);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.fail("服务器内部错误");
    }
}
