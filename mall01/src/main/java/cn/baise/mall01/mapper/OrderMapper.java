package cn.baise.mall01.mapper;

import cn.baise.mall01.entity.Order;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}