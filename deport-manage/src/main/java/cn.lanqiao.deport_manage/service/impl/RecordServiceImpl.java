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

    //管理员拒绝只修改记录状态，不允许物品出入库
    @Override
    public int updateRecord(Record record) {
        recordMapper.updateStateById(record.getId(), record.getState());
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


    //申请入库记录
    @Override
    public int addReUser(Goods goods) {
        Record record = new Record();
        record.setNumber(goods.getNumber());
        record.setUsername(goods.getUsername());
        record.setType("入库");
        record.setGoodsName(goods.getGoodsName());
        record.setState("暂未处理");
        RGoods rGoods = rGoodsMapper.getRGoods(goods.getGoodsName(), goods.getUsername());

        if (rGoods == null) {
            rGoodsMapper.addRGoods(goods);
        }
        recordMapper.addRecord(record);
        return 1;
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
        //查看该用户和物品是否存在
        Goods goods = goodsMapper.selectByRecord(record.getGoodsName(), record.getUsername());
        if (goods == null) {
            RGoods rGoods = rGoodsMapper.getRGoods(record.getGoodsName(), record.getUsername());
            if (rGoods != null) {
                Goods gd = new Goods();
                gd.setGoodsName(rGoods.getGoodsName());
                gd.setPrice(rGoods.getPrice());
                gd.setMax(rGoods.getMax());
                gd.setContext(rGoods.getContext());
                gd.setNumber(record.getNumber());
                gd.setUsername(rGoods.getUsername());
                gd.setMin(rGoods.getMin());
                gd.setPrice(rGoods.getPrice());
                recordMapper.updateStateById(record.getId(), record.getState());
                goodsMapper.addGoods(gd);
                rGoodsMapper.deleteRGoods(rGoods.getAid());
                return 1;
            }
        } else {
            int num = goodsMapper.selectByRecord(record.getGoodsName(),record.getUsername()).getNumber();
            System.out.println(num);
            if (num + record.getNumber() <= 1000) {
                goodsMapper.addGNum(record.getNumber(), goods.getId());
                recordMapper.updateStateById(record.getId(), record.getState());
            } else {
                recordMapper.updateStateById(record.getId(),"添加失败");
                rGoodsMapper.deleteR(record.getGoodsName(), record.getUsername());
            }
            return 1;
        }
        return 0;
    }


    //同意出库请求
    @Override
    public int agreeRemove(Record record) {
        Goods g = goodsMapper.selectByRecord(record.getGoodsName(), record.getUsername());
        //出库数量大于库存数量时
        if (record.getNumber() > g.getNumber()){
            recordMapper.updateStateById(record.getId(),"出库失败");
            rGoodsMapper.deleteR(record.getGoodsName(), record.getUsername());
            return 1;
        }
        //部分出库
        if (record.getNumber() < g.getNumber() && record.getNumber() > 0) {
            recordMapper.updateStateById(record.getId(), record.getState());
            goodsMapper.updateGNum(record.getNumber(), g.getId());
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
