package com.xj.demo;

import com.xj.demo.entity.Essay;
import com.xj.demo.mapper.EssayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@RestController
public class Test {
    @Autowired
    private EssayMapper essayMapper;

    @GetMapping("/add")
    public String add() {
        Essay essay = new Essay();
        String text = "黄河是福建晋江人，仅有初中学历的他，早年在国企做电工，下岗后和朋友先后开办过弹簧厂、塑料厂。2003年9月，他在村里开办了一家玩具厂，并从香港买回台湾生产的玩具枪进行仿制，得益于当地众多的海外侨胞，黄河生产的黄河牌玩具枪一度远销捷克、菲律宾等国。";
        String text1 = "明确了枪支认定标准，对不能发射制式弹药的非制式枪支，枪口比动能大于等于1.8J/CM2即认定为枪支。2011年4月，黄河的一个下游经销商在河南许昌被警方抓获，之后黄河进入警方视野。";
        String text2 = "被抓后，黄河牌玩具枪在市场上逐渐消匿。不过，随着短视频社交平台的兴起，又有一些人在社交平台上展示其拥有的黄河牌玩具枪，称其为“情怀玩具”。还有很多账号在发布玩具枪视频时";
        for (int i = 2; i < 100; i++) {
            String a = null;
            switch ((i + 10) % 3) {
                case 0:
                    a = text;
                    break;
                case 1:
                    a = text1;
                    break;
                case 2:
                    a = text2;
                    break;

            }
            essay.setId(i + 10);
            essay.setUserId((int)(Math.random()*25+1));
            essay.setTitle(ud(a,(int)(Math.random() * 4+3)));
            essay.setContent(ud(a,(int)(Math.random() * 20+15)));
            essay.setCreateTime(new Date());
            essay.setType((int)(Math.random()*6 + 1));
            essay.setStatus((int) (Math.random() * 2 ));
            essay.setScore((double) (Math.random() * 200 + 0.4*i));
            essayMapper.insert(essay);

        }
        return "suc";
    }
    public static String ud(String s,int i){
        int r = (int)(Math.random()*(s.length()-i));
        return s.substring(r,i+r);
    }
}
