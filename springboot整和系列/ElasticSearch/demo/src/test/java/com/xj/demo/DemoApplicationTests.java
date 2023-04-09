package com.xj.demo;

import com.xj.demo.entity.Essay;
import com.xj.demo.mapper.EssayMapper;
import com.xj.demo.mapper.es.EssayRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private EssayMapper essayMapper;
    @Autowired
    private EssayRepository essayRepository;

    /**
     * 增加、修改
     */
    @Test
    void save() {
//        essayRepository.save(essayMapper.selectById(1));
        List<Essay> essays = essayMapper.selectList(null);
        essayRepository.saveAll(essays);
    }

    /**
     * 删除
     */
    @Test
    void delete() {
        essayRepository.deleteById(2l);
    }

    /**
     * 查所有
     */
    @Test
    void selectAll() {
        Iterable<Essay> all = essayRepository.findAll();
        Iterator<Essay> iterator = all.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 通过id查
     */
    @Test
    void selectById() {
        Optional<Essay> essay = essayRepository.findById(24l);
        System.out.println(essay);
    }

    /**
     * 根据内容模糊查询
     */
    @Test
    void selectByContent() {
        List<Essay> essays = essayRepository.selectByContent("海外");
        System.out.println(essays);
    }

    /**
     * 排序查
     */
    @Test
    void selectBySort() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Iterable<Essay> essays = essayRepository.findAll(sort);
        Iterator<Essay> iterator = essays.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 多个条件排序
     */
    @Test
    void selectBySorts() {
        ArrayList<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, "userId"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sort = Sort.by(orders);
        Iterator<Essay> iterator = essayRepository.findAll(sort).iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    /**
     * 限制数据量
     */
    @Test
    void selectByPage() {
        Pageable pageable = Pageable.ofSize(10);
        Page<Essay> page = essayRepository.findAll(pageable);
        page.forEach(System.out::println);
    }

    /**
     * 分页查
     */
    @Test
    void selectByPage1() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<Essay> page = essayRepository.findAll(pageable);
        page.forEach(System.out::println);
    }

    /**
     * 分页排序
     */
    @Test
    void selectByPageAndSort() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(1, 10, sort);
        Page<Essay> page = essayRepository.findAll(pageable);
        page.forEach(System.out::println);
    }
}
