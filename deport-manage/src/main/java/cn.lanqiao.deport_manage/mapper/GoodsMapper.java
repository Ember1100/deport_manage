package cn.lanqiao.deport_manage.mapper;

import cn.lanqiao.deport_manage.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {
    //查询所有商品信息
    List<Goods> getAllGoods();


    //根据用户名字查询查询物品
    List<Goods> getGoodsWithUsername(String username);

    int addGoods(Goods goods);

    int deleteGoods(int id);

    int delete(@Param("goodsName")String goodsName,@Param("username")String username);

    int updateGoods(Goods goods);

    List<Goods> getGoodsWithName(String goodsName);


    Goods selectById(int id);

    Goods selectByRecord(@Param("goodsName")String goodsName,@Param("username") String username);

    List<Goods> getGoodsWithUserGoods(@Param("goodsName") String goodsName, @Param("username") String username);

   int updateGNum(@Param("num") int num,@Param("id") int id);

    int addGNum(@Param("num") int num,@Param("id") int id);

    int getNum(int id);

}
