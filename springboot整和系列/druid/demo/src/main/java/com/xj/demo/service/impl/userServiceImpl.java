package com.xj.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.demo.mapper.UserMapper;
import com.xj.demo.pojo.User;
import com.xj.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Service
public class userServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
