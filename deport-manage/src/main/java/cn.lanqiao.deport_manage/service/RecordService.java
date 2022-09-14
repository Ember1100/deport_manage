package cn.lanqiao.deport_manage.service;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.Record;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface RecordService {
     List<Record> getAllRecord();

    PageInfo<Record> getRecordPage(int pageNo, int pageSize);


    int updateState(Record record);

    int updateRecord(Record record);

    Record select(int id);

    int updateGoodsNumber(Record record);


    List<Record> getUserRecord(String username);

    int deleteAddOne(int id,int number);
    int delRecord(int id);

    int delRecords(List<Integer> ids);

    int addReUser(Goods goods);
    //同意用户添加物品
    int agreeAdd(Record record) ;

    //同意出库请求
    int agreeRemove(Record record);

}
