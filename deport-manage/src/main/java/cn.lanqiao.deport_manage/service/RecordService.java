package cn.lanqiao.deport_manage.service;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.Record;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface RecordService {
     List<Record> getAllRecord();

    PageInfo<Record> getRecordPage(int pageNo, int pageSize);

     int addRecord(Record record);

    int updateState(Record record);

    int updateRecord(int id,String state);

    Record select(int id);

    int updateGoodsNumber(Record record);


    List<Record> getUserRecord(String username);


    int delRecord(int id);

    int delRecords(List<Integer> ids);

    int addReUser(Goods goods);
    //同意用户添加物品
    int agreeAdd(Record record) ;

}
