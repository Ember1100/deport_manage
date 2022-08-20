package cn.baise.mall01.service.impl;

import cn.baise.mall01.entity.Book;
import cn.baise.mall01.mapper.BookMapper;
import cn.baise.mall01.service.BookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper ;

    @Override
    public Book get(int id) {

        return bookMapper.selectByPrimaryKey(id);
    }
}
