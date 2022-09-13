package cn.lanqiao.deport_manage.service.impl;


import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.RGoods;
import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.mapper.GoodsMapper;
import cn.lanqiao.deport_manage.mapper.RGoodsMapper;
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
    private RGoodsMapper rGoodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;

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

    //同意出库
    @Override
    public int updateState(Record record) {
        goodsMapper.delete(record.getGoodsName(), record.getUsername());
        recordMapper.updateStateById(record.getId(), record.getState());
        return 1;
    }

    @Override
    public int updateRecord(int id, String state) {
        return recordMapper.updateStateById(id, state);
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
        int i = rGoodsMapper.addRGoods(goods);
        if (i == 1) {
            record.setNumber(goods.getNumber());
            record.setUsername(goods.getUsername());
            record.setType("入库");
            record.setGoodsName(goods.getGoodsName());
            record.setState("暂未处理");
            recordMapper.addRecord(record);
            return 1;
        }
        return 0;
    }


    @Override
    public int deleteAddOne(int id, int number) {
        System.out.println(id);
        Goods goods = goodsMapper.selectById(id);
        //储存用户提交出库的物品
        Record record = new Record();
        record.setGoodsName(goods.getGoodsName());
        record.setUsername(goods.getUsername());
        record.setNumber(number);
        record.setType("出库");
        record.setState("暂未处理");
        recordMapper.addRecord(record);
        return 1;
    }

    //同意入库请求
    @Override
    public int agreeAdd(Record record) {
        RGoods rGoods = rGoodsMapper.getRGoods(record.getGoodsName(),record.getUsername());
       if (rGoods!=null) {
           Goods gd = new Goods();
           gd.setGoodsName(rGoods.getGoodsName());
           gd.setPrice(rGoods.getPrice());
           gd.setMax(rGoods.getMax());
           gd.setContext(rGoods.getContext());
           gd.setNumber(rGoods.getNumber());
           gd.setUsername(rGoods.getUsername());
           gd.setMin(rGoods.getMin());
           gd.setPrice(rGoods.getPrice());
           recordMapper.updateStateById(record.getId(), record.getState());
           goodsMapper.addGoods(gd);
           rGoodsMapper.deleteRGoods(rGoods.getAid());
           return 1;
       }

        return 0;
    }

    //同意出库请求
    @Override
    public int agreeRemove(Record record) {
        Goods g = goodsMapper.selectByRecord(record.getGoodsName(), record.getUsername());
        if (record.getNumber() > g.getNumber()) return 0;
        //部分出库
        if (record.getNumber() < g.getNumber() && record.getNumber() > 0) {
            recordMapper.updateStateById(record.getId(), record.getState());
            goodsMapper.updateGNum(record.getNumber(), g.getId());
            g.setNumber(g.getNumber() - record.getNumber());
            return 1;
        } else if (record.getNumber() == g.getNumber()) {
            //全部出库
            goodsMapper.delete(record.getGoodsName(), record.getUsername());
            recordMapper.updateStateById(record.getId(), record.getState());
            return 1;
        }
        return 0;
    }


}
