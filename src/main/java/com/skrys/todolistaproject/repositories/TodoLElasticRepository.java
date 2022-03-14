package com.skrys.todolistaproject.repositories;

import com.skrys.todolistaproject.entity.elastic.TodoLElastic;
import com.skrys.todolistaproject.entity.pg.TodoL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoLElasticRepository extends ElasticsearchRepository<TodoLElastic, Long> {

    Page<TodoLElastic> findByUsername(String username, Pageable pageable);
    List<TodoLElastic> findAll();
    void deleteByBusinessKey(String bId);
/*
    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
    Page<TodoLElastic> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);*/
}