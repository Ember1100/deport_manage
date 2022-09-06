package cn.lanqiao.deport_manage.service.impl;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.entity.Record;
import cn.lanqiao.deport_manage.mapper.GoodsMapper;
import cn.lanqiao.deport_manage.mapper.RecordMapper;
import cn.lanqiao.deport_manage.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RecordMapper recordMapper;
    @Override
    public List<Goods> getAllGoods() {
        List<Goods> list = null;
        list = goodsMapper.getAllGoods();
        return list;
    }


    @Override
    public PageInfo<Goods> getPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<Goods> list = goodsMapper.getAllGoods();
        PageInfo<Goods> page = new PageInfo<>(list);
        return page;
    }

    //根据用户名字查询查询物品
    @Override
    public List<Goods> getGoodsWithUsername(String username) {
        return goodsMapper.getGoodsWithUsername(username);
    }

    @Override
    public int saveGoods(Goods goods) {
        if (goods.getId() != null) {
            return goodsMapper.updateGoods(goods);
        } else {
            Record record  = new Record();
            record.setGoodsName(goods.getGoodsName());
            record.setUsername(goods.getUsername());
            record.setNumber(goods.getNumber());
            record.setType("入库");
            record.setState("暂未处理");
            goodsMapper.addGoods(goods);
            recordMapper.addRecord(record);
            return 1 ;
        }
    }



    @Override
    public int del(int id) {
        return goodsMapper.deleteGoods(id);
    }

    @Override
    public List<Goods> getGoodsWithName(String goodsName) {
        return goodsMapper.getGoodsWithName(goodsName);
    }


}
