package cn.baise.mall01.mapper;

import cn.baise.mall01.entity.OrderDetail;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderDetailMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}