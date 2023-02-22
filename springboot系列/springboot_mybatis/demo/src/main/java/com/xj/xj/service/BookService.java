package com.xj.xj.service;

import com.xj.xj.domain.Book;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */

public interface BookService {
    int insert(Book book);
    List selectAll();
}
