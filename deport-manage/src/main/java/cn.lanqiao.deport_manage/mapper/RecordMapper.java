package cn.lanqiao.deport_manage.mapper;

import cn.lanqiao.deport_manage.entity.Record;
import org.apache.ibatis.annotations.Param;

import java.awt.print.Book;
import java.util.List;

public interface RecordMapper {


    //查询所有记录信息
    public List<Record> getAllRecord();

    //新增记录
    public int addRecord(Record record);

    int updateStateById(@Param("id") Integer id,@Param("state") String state);

    Record selectRecord(int id);

    List<Record> getUserRecord(String username);

    int updateNumber(Record record);

    int delRecord(int id);
    int delRecords(List<Integer> ids);


}