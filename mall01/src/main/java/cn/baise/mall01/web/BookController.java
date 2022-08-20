package cn.baise.mall01.web;

import cn.baise.mall01.entity.Book;
import cn.baise.mall01.service.BookService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService ;

    @RequestMapping("/test")
    public Book getPage(Integer id){

        return bookService.get(1);
    }
}
