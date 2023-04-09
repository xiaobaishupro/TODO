package com.xj.demo.mapper.es;

import com.xj.demo.entity.Essay;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@Component
public interface EssayRepository extends ElasticsearchRepository<Essay, Long> {
    /**
     * 使用@Query注解自定义查询语句，其中的?是占位符，0表示第一个参数
     * @param content
     * @return
     */
    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [\n" +
            "        {\n" +
            "          \"match\": {\n" +
            "            \"content\": \"?0\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }")
    List<Essay> selectByContent(String content);
}