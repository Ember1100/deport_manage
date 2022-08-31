package cn.lanqiao.deport_manage.service;

import cn.lanqiao.deport_manage.entity.Goods;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface GoodsService {
    public List<Goods> getAllGoods();

    public PageInfo<Goods> getPage(int pageNo, int pageSize);

    public List<Goods> getGoodsWithUsername(String username);

    public int saveGoods(Goods goods);

    public int del(int id);

    List<Goods> getGoodsWithName(String goodsName);

}
