package cn.lanqiao.deport_manage.mapper;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.RGoods;
import org.apache.ibatis.annotations.Param;

public interface RGoodsMapper {
    int addRGoods(Goods goods);

    RGoods getRGoods(@Param("goodsName") String goodsName,@Param("username") String username);

    int deleteRGoods(int aid);

    int deleteR(@Param("goodsName") String goodsName,@Param("username") String username);
}
