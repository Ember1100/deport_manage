package cn.lanqiao.deport_manage.mapper;

import cn.lanqiao.deport_manage.entity.Record;

import java.util.List;

public interface RecordMapper {


    //查询所有记录信息
    public List<Record> getAllRecord();

    //新增记录
    public int addRecord(Record record);

    List<Record> getUserRecord(String username);

}