package cn.lanqiao.deport_manage.service;

import cn.lanqiao.deport_manage.entity.Record;
import com.github.pagehelper.PageInfo;

import java.awt.print.Book;
import java.util.List;

public interface RecordService {
    public List<Record> getAllRecord();

    public PageInfo<Record> getRecordPage(int pageNo, int pageSize);

    public int addRecord(Record record);

    int updateState(Integer id,String state);

    Record select(int id);

    int updateGoodsNumber(Record record);
}
