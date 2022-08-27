package cn.lanqiao.deport_manage.service.impl;


import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.mapper.RecordMapper;
import cn.lanqiao.deport_manage.service.RecordService;
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


}
