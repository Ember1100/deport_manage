package cn.baise.mall01.mapper;

import cn.baise.mall01.entity.Type;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TypeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);
}