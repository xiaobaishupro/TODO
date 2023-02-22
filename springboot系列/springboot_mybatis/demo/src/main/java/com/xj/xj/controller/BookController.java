package com.xj.xj.controller;

import com.xj.xj.domain.Book;
import com.xj.xj.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @RequestMapping("/hello")
    @ResponseBody()
    public String insert(){
        Book book = new Book();
        book.setAuthor("xudada");
        book.setAuthor("adaa");
        book.setPrice(33.45f);
        book.setCreateTime(new Date());
        book.setDescription("你好");
        bookService.insert(book);
        return "hello";
    }

    @RequestMapping("/select")
    @ResponseBody()
    public List tt(){
        return bookService.selectAll();
    }
}
