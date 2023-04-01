package com.xj.demo.service;

import com.xj.demo.pojo.User;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
public interface UserService {

    List<User> getList();

    User getUserById( Integer id);

    Integer add(User user);

    void update(User user);

    void delete(Integer userId);
}
