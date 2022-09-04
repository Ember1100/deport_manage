package cn.lanqiao.deport_manage.service.impl;


import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.mapper.RecordMapper;
import cn.lanqiao.deport_manage.service.RecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Override
    public List<Record> getAllRecord() {
        List<Record> list =null;
        list = recordMapper.getAllRecord();
        return list;
    }

    @Override
    public PageInfo<Record> getRecordPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Record> allRecord = recordMapper.getAllRecord();
        PageInfo<Record> pageInfo = new PageInfo<>(allRecord);
        return pageInfo;
    }

    @Override
    public int addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    @Override
    public List<Record> getUserRecord(String username) {
        return recordMapper.getUserRecord(username);
    }

    @Override
    public int delRecord(int id) {
        return recordMapper.delRecord(id);
    }


    @Override
    public int delRecords(List<Integer> ids) {
        return recordMapper.delRecords(ids);
    }


}
