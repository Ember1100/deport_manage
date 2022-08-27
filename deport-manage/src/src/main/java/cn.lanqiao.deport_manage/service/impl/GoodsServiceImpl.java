package cn.lanqiao.deport_manage.service.impl;

import cn.lanqiao.deport_manage.entity.Goods;
import cn.lanqiao.deport_manage.mapper.GoodsMapper;
import cn.lanqiao.deport_manage.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getAllGoods() {
        List<Goods> list =null;
        list = goodsMapper.getAllGoods();
        return list;
    }

}
