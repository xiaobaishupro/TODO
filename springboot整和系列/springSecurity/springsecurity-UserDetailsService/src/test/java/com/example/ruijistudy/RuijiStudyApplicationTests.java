package com.example.ruijistudy;

import com.example.ruijistudy.entity.SysUser;
import com.example.ruijistudy.mapper.SysUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RuijiStudyApplicationTests {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Test
    void contextLoads() {
        SysUser sysUser = new SysUser();
        SysUser sysUser1 = sysUserMapper.selectById(1);
        System.out.println(sysUser1.toString());
    }

}
