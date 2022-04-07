package com.skrys.todolistaproject.controller;

import com.skrys.todolistaproject.entity.elastic.TodoLElastic;
import com.skrys.todolistaproject.entity.mgdb.Event;
import com.skrys.todolistaproject.entity.pg.TodoL;
import com.skrys.todolistaproject.service.EventToTodoIService;
import com.skrys.todolistaproject.service.TodoLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class TodoLController {

    @Autowired
    private TodoLService todoLService;

    @Autowired
    private EventToTodoIService eventService;

    private final Logger logger = LoggerFactory.getLogger(TodoLController.class);
    //POST

    @PostMapping("/addTODO")
    public Event addEventTodoL(@RequestBody TodoL todoL) {
        todoL.setBusinessKey(java.util.UUID.randomUUID().toString());
        Event event = new Event(java.util.UUID.randomUUID().toString(), "create", todoL, "new", System.nanoTime());
        logger.info("Course object {}", event.toString());

        return todoLService.saveEvent(event);
    }
    @GetMapping("/todo_items_count")
    public long getAllEventsCount() {
        return eventService.countEvents();
    }

    @GetMapping("/getEvents")
    public List<Event> getAllEvents() {
        return eventService.getEvents();
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
