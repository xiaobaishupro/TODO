package com.example.ruijistudy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruijistudy.entity.SysUser;
import com.example.ruijistudy.mapper.SysUserMapper;
import com.example.ruijistudy.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Service
@Slf4j
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService, UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",s);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList(sysUser.getRole());
        return new User(sysUser.getUsername(),sysUser.getPassword(),list);
    }
}
