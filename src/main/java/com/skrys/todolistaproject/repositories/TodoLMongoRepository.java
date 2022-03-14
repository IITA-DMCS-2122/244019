package com.skrys.todolistaproject.repositories;


import com.skrys.todolistaproject.entity.mgdb.TodoLMGDB;
import com.skrys.todolistaproject.entity.pg.TodoL;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoLMongoRepository extends MongoRepository<TodoLMGDB, Long> {

    TodoLMGDB findByTopic(String topic);

    List<TodoLMGDB> findAllByUsername(String username);
    void deleteByBusinessKey(String bId);

    List<TodoLMGDB> findAll();

}