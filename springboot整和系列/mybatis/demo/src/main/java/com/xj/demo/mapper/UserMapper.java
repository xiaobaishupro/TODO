package com.xj.demo.mapper;

import com.xj.demo.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
public interface UserMapper {

    List<User> getList();

    User getUserById(@Param("userId") Integer id);

    Integer add( User user);

    void update(User user);

    void delete(@Param("userId") Integer userId);
}
