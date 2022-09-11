package cn.lanqiao.deport_manage.mapper;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.entity.User;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    //查询所有商品信息
    List<Goods> getAllGoods();


    //根据用户名字查询查询物品
    List<Goods> getGoodsWithUsername(String username);

    int addGoods(Goods goods);

    int deleteGoods(int id);

    int delete(@Param("goodsName")String goodsName,@Param("username")String username,@Param("number")int number);

    int updateGoods(Goods goods);

    List<Goods> getGoodsWithName(String goodsName);


    Goods selectById(int id);

    List<Goods> getGoodsWithUserGoods(@Param("goodsName") String goodsName, @Param("username") String username);



}
