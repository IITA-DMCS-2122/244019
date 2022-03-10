package com.skrys.todolistaproject.repositories;


import com.skrys.todolistaproject.entity.TodoL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoLRepository extends JpaRepository<TodoL, Integer> {

    TodoL findByTopic(String topic);

    List<TodoL> findAllByUsername(String username);

    List<TodoL> findAll();
}
