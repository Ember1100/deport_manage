package cn.lanqiao.deport_manage.mapper;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.Record;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    //查询所有商品信息
    public List<Goods> getAllGoods();


    //根据用户名字查询查询物品
    public List<Goods> getGoodsWithUsername(String username);

    public int addGoods(Goods goods);

    int deleteGoods(int id);

    public int updateGoods(Goods goods);

    List<Goods> getGoodsWithName(String goodsName);
}