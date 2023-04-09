package com.xj.demo;

import com.xj.demo.entity.Essay;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

/**
 * @author 努力的派大星
 * @version 1.0
 */
@SpringBootTest
public class EsTemplateTest {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    void save(){
        Essay essay = new Essay();
        essay.setId(999);
        essay.setTitle("swwxwwxwdwdwdwdwdddw");
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(essay.getId()+"")
                .withObject(essay)
                .build();
        IndexCoordinates indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(essay.getClass());
        elasticsearchOperations.index(indexQuery, indexCoordinates);
    }
    @Test
    public void testQuery() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(new MatchAllQueryBuilder())
                .build();

        SearchHits<Essay> searchHits = elasticsearchOperations.search(searchQuery, Essay.class);
        long count = searchHits.getTotalHits();
        System.out.println(count);
        List<SearchHit<Essay>> list = searchHits.getSearchHits();
        for (SearchHit hit:list) {
            System.out.println(hit.getContent());
        }
    }
}
