package cn.baise.mall01.mapper;

import cn.baise.mall01.entity.Book;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKey(Book record);
}