package com.skrys.todolistaproject.service;


import com.skrys.todolistaproject.entity.mgdb.TodoLMGDB;
import com.skrys.todolistaproject.entity.pg.TodoL;
import com.skrys.todolistaproject.repositories.TodoLMongoRepository;
import com.skrys.todolistaproject.repositories.TodoLRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoLService {
    @Autowired
    private TodoLRepository todoLRepository;
    @Autowired
    private TodoLMongoRepository todoLMongoRepository;


    public TodoLService(TodoLRepository todoLRepository, TodoLMongoRepository todoLMongoRepository) {
        this.todoLRepository = todoLRepository;
        this.todoLMongoRepository = todoLMongoRepository;
    }

    public TodoLMGDB pgTodoLtoMongoTodoLMGDB(TodoL todo){
        return new TodoLMGDB(todo.getId(), todo.getPriority(), todo.getTopic(), todo.getStatus(), todo.getDescription(), todo.getUsername());
    }

    //POST
    public TodoL saveTodoL(TodoL todo) {
        TodoL tmpTodoL = todoLRepository.save(todo);
        todoLMongoRepository.save(pgTodoLtoMongoTodoLMGDB(tmpTodoL));
        return tmpTodoL;
    }
    //GET
    public List<TodoL> getTODOs() {
        return todoLRepository.findAll();
    }

    public TodoL getTodoLById(int id) {
        return todoLRepository.findById(id).orElse(null);
    }
    public TodoL getTodoLByTopic(String topic) {
        return todoLRepository.findByTopic(topic);
    }
    public List<TodoL> getTodosForUser(String username) {
        return todoLRepository.findAllByUsername(username);
    }

    //PUT
    public TodoL updateTodoL(TodoL todoL) {
        System.out.println("updates");
        TodoL existing_todo = todoLRepository.findById(todoL.getId()).orElse(null);
        existing_todo.setPriority(todoL.getPriority());
        existing_todo.setTopic(todoL.getTopic());
        existing_todo.setStatus(todoL.getStatus());
        existing_todo.setDescription(todoL.getDescription());
        existing_todo.setUsername(todoL.getUsername());
        return todoLRepository.save(existing_todo);
    }

    //DELETE
    public String deleteTodoL(int id) {
        todoLRepository.deleteById(id);
        todoLMongoRepository.deleteById(id);
        return id + " id -> course removed/completed";
    }


    public List<TodoL> getTodoLs() {
        return todoLRepository.findAll();
    }


}
