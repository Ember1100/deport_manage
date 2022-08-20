package cn.baise.mall01.mapper;

import cn.baise.mall01.entity.cart;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface cartMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(cart record);

    int insertSelective(cart record);


    cart selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(cart record);

    int updateByPrimaryKey(cart record);
}