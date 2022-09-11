package cn.lanqiao.deport_manage.service;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsService {
    List<Goods> getAllGoods();



    PageInfo<Goods> getPage(int pageNo, int pageSize);

    List<Goods> getGoodsWithUsername(String username);

    int saveGoods(Goods goods);

    int del(int id);

    int deleteGoods(String goodsName,String username);

    List<Goods> getGoodsWithName(String goodsName);

    int deleteAddOne(int id);

    List<Goods> getGoodsWithUserGoods(String goodsName,String username);

}
