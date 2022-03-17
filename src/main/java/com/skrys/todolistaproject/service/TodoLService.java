package com.skrys.todolistaproject.service;


import com.skrys.todolistaproject.entity.elastic.TodoLElastic;
import com.skrys.todolistaproject.entity.mgdb.TodoLMGDB;
import com.skrys.todolistaproject.entity.pg.TodoL;
import com.skrys.todolistaproject.repositories.TodoLElasticRepository;
import com.skrys.todolistaproject.repositories.TodoLMongoRepository;
import com.skrys.todolistaproject.repositories.pg1.TodoLPg1Repository;

import com.skrys.todolistaproject.repositories.pg2.TodoLPg2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TodoLService {
    @Autowired
    private TodoLPg1Repository todoLRepository;
    @Autowired
    private TodoLPg2Repository todoLPg2Repository;
    @Autowired
    private TodoLMongoRepository todoLMongoRepository;
    @Autowired
    private TodoLElasticRepository todoLElasticRepository;

    public TodoLService(TodoLPg1Repository todoLRepository, TodoLPg2Repository todoLPg2Repository, TodoLMongoRepository todoLMongoRepository, TodoLElasticRepository todoLElasticRepository) {
        this.todoLRepository = todoLRepository;
        this.todoLPg2Repository = todoLPg2Repository;
        this.todoLMongoRepository = todoLMongoRepository;
        this.todoLElasticRepository = todoLElasticRepository;
    }

    public TodoLMGDB pgTodoLtoMongoTodoLMGDB(TodoL todo){
        return new TodoLMGDB(todo.getId(), todo.getPriority(), todo.getTopic(), todo.getStatus(), todo.getDescription(), todo.getUsername(), todo.getBusinessKey());
    }

    public TodoLElastic pgTodoLtoElasticTodoLElastic(TodoL todo){
        return new TodoLElastic(todo.getId(), todo.getPriority(), todo.getTopic(), todo.getStatus(), todo.getDescription(), todo.getUsername(), todo.getBusinessKey());
    }

    public String hashID(long id)  {

        return UUID.randomUUID().toString()+Long.toString(id)+UUID.randomUUID().toString();

    }

    //POST
    public TodoL saveTodoL(TodoL todo) {
        todo.setBusinessKey(hashID(todo.getId()));
        TodoL tmpTodoL = todoLRepository.save(todo);
        System.out.println(tmpTodoL.getId());
        //todoLPg2Repository.save(tmpTodoL);//zapisywanie do analitics
        System.out.println(todoLPg2Repository.save(tmpTodoL).getId());
        todoLMongoRepository.save(pgTodoLtoMongoTodoLMGDB(tmpTodoL));
        todoLElasticRepository.save(pgTodoLtoElasticTodoLElastic(todo));
        return tmpTodoL;
    }
    //GET
    /*public List<TodoL> getTODOs() {
        return todoLRepository.findAll();
    }*/

    public TodoL getTodoLById(long id) {
        return todoLRepository.findById(id).orElse(null);
    }
    public TodoL getTodoLByTopic(String topic) {
        return todoLRepository.findByTopic(topic);
    }
    public List<TodoL> getTodosForUser(String username) {
        return todoLRepository.findAllByUsername(username);
    }

    //PUT
    public TodoL updateTodoLById(TodoL todoL) {
        System.out.println("updates");
        TodoL existing_todo = todoLRepository.findById(todoL.getId()).orElse(null);
        existing_todo.setPriority(todoL.getPriority());
        existing_todo.setTopic(todoL.getTopic());
        existing_todo.setStatus(todoL.getStatus());
        existing_todo.setDescription(todoL.getDescription());
        existing_todo.setUsername(todoL.getUsername());
        //existing_todo.setBusinessKey(todoL.getBusinessKey());
        TodoL tTodoL = todoLRepository.save(existing_todo);
        todoLMongoRepository.save(pgTodoLtoMongoTodoLMGDB(tTodoL));
        todoLElasticRepository.save(pgTodoLtoElasticTodoLElastic(tTodoL));
        return tTodoL;
    }
    public TodoL updateTodoLBybId(TodoL todoL) {
        System.out.println("updatesBID");
        TodoL existing_todo = todoLRepository.findByBusinessKey(todoL.getBusinessKey());
        existing_todo.setPriority(todoL.getPriority());
        existing_todo.setTopic(todoL.getTopic());
        existing_todo.setStatus(todoL.getStatus());
        existing_todo.setDescription(todoL.getDescription());
        existing_todo.setUsername(todoL.getUsername());
        //existing_todo.setBusinessKey(todoL.getBusinessKey());
        TodoL tTodoL = todoLRepository.save(existing_todo);
        todoLMongoRepository.save(pgTodoLtoMongoTodoLMGDB(tTodoL));
        todoLElasticRepository.save(pgTodoLtoElasticTodoLElastic(tTodoL));
        return tTodoL;
    }

    //DELETE
    @Transactional
    public String deleteTodoL(String bId) {
        todoLRepository.deleteByBusinessKey(bId);
        todoLMongoRepository.deleteByBusinessKey(bId);
        todoLElasticRepository.deleteByBusinessKey(bId);
        //todoLRepository.deleteById(id);
        //todoLMongoRepository.deleteById(id);
        return bId + " id -> course removed/completed";
    }


    public List<TodoL> getTodoLs() {
        return todoLRepository.findAll();
    }


    public List<TodoLElastic> getElTodoLs() {
        return (List<TodoLElastic>) todoLElasticRepository.findAll();
    }

}
