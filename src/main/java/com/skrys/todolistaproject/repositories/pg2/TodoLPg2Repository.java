package com.skrys.todolistaproject.repositories.pg2;


import com.skrys.todolistaproject.entity.pg.TodoL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoLPg2Repository extends JpaRepository<TodoL, Long> {

    TodoL findByTopic(String topic);
    TodoL findByBusinessKey(String bId);
    void deleteByBusinessKey(String bId);

    List<TodoL> findAllByUsername(String username);

    List<TodoL> findAll();
}
