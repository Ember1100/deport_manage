package cn.baise.mall01.mapper;

import cn.baise.mall01.entity.Admin;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);



    Admin selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}