package com.xj.xj.service.impl;

import com.xj.xj.domain.Book;
import com.xj.xj.domain.mapper.BookMapper;
import com.xj.xj.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Override
    public int insert(Book book){
        return bookMapper.insert(book);
    }

    @Override
    public List selectAll() {
        return bookMapper.selectAll();
    }
}
