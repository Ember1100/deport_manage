package cn.lanqiao.deport_manage.service;

import cn.lanqiao.deport_manage.entity.Record;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RecordService {
    public List<Record> getAllRecord();

    public PageInfo<Record> getRecordPage(int pageNo, int pageSize);

    public int addRecord(Record record);

    List<Record> getUserRecord(String username);

    int delRecord(int id);

    int delRecords(List<Integer> ids);
}
