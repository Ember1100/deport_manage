package cn.lanqiao.deport_manage.service.impl;


import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.mapper.GoodsMapper;
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

    @Autowired
    private GoodsMapper goodsMapper;

    private static Goods good;

    @Override
    public List<Record> getAllRecord() {
        List<Record> list = null;
        list = recordMapper.getAllRecord();
        return list;
    }

    @Override
    public List<Record> getUserRecord(String username) {
        return recordMapper.getUserRecord(username);
    }

    @Override
    public PageInfo<Record> getRecordPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Record> allRecord = recordMapper.getAllRecord();
        PageInfo<Record> pageInfo = new PageInfo<>(allRecord);
        return pageInfo;
    }

    @Override
    public int addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    @Override
    public int updateState(Integer id,String state,String goodsName,String username) {
        goodsMapper.delete(goodsName,username);
        recordMapper.updateStateById(id,state);
        return 1;
    }

    @Override
    public Record select(int id) {
        return recordMapper.selectRecord(id);
    }

    @Override
    public int updateGoodsNumber(Record record) {
        return recordMapper.updateNumber(record);
    }

    @Override
    public int delRecord(int id) {
        return recordMapper.delRecord(id);
    }


    @Override
    public int delRecords(List<Integer> ids) {
        return recordMapper.delRecords(ids);
    }

    @Override
    public int addReUser(Goods goods) {
        Record record = new Record();
        record.setNumber(goods.getNumber());
        record.setUsername(goods.getUsername());
        record.setType("入库");
        record.setGoodsName(goods.getGoodsName());
        record.setState("暂未处理");
        good = goods;
        return recordMapper.addRecord(record);
    }


    @Override
    public int agreeAdd(Integer id, String state) {
        System.out.println(good);
        recordMapper.updateStateById(id, state);
        goodsMapper.addGoods(good);
        return 1;
    }


}
