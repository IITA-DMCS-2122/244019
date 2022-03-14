package com.skrys.todolistaproject.controller;

import com.skrys.todolistaproject.entity.elastic.TodoLElastic;
import com.skrys.todolistaproject.entity.pg.TodoL;
import com.skrys.todolistaproject.service.TodoLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3030"})
public class TodoLController {

    @Autowired
    private TodoLService todoLService;



    private final Logger logger = LoggerFactory.getLogger(TodoLController.class);
    //POST
    @PostMapping("/addTODO")
    public TodoL addTodoL(@RequestBody TodoL todoL) {
        logger.info("Course object {}", todoL.toString());

        return todoLService.saveTodoL(todoL);
    }

    //GET
    @GetMapping("/TODOs")
    public List<TodoL> getAllTodoL() {
        return todoLService.getTodoLs();
    }
    @GetMapping("/elTODOs")
    public List<TodoLElastic> getAllElTodoL() {
        return todoLService.getElTodoLs();
    }
    @GetMapping("/todoById/{id}")
    public TodoL findTodoLById(@PathVariable int id) {
        return todoLService.getTodoLById(id);
    }
    @GetMapping("/todoLByName/{topic}")
    public TodoL findTodoLByTopic(@PathVariable String topic) {
        return todoLService.getTodoLByTopic(topic);
    }
    @GetMapping("/listTodosByUser/{username}")
    public List<TodoL> findTodoLsByUsername(@PathVariable String username) {
        return todoLService.getTodosForUser(username);
    }

    //PUT
    @PutMapping("/update")
    public TodoL updateTodoL(@RequestBody TodoL todoL)
    {
        System.out.println("UPDATED");
        return todoLService.updateTodoLById(todoL);
    }

    @PutMapping("/updatebId")
    public TodoL updateTodoLBybId(@RequestBody TodoL todoL)
    {
        System.out.println("UPDATED");
        return todoLService.updateTodoLBybId(todoL);
    }


    //DELETE
    @DeleteMapping("/delete/{bId}")
    public String deleteTodoL(@PathVariable String bId) {
        return todoLService.deleteTodoL(bId);
    }

}
