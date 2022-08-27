package cn.lanqiao.deport_manage.web;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.service.GoodsService;
import cn.lanqiao.deport_manage.utils.Result;
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
        List<Goods> list =goodsService.getAllGoods();
        return Result.success(list);
    }

}
