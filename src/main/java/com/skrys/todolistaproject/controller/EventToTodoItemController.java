package com.skrys.todolistaproject.controller;

import com.skrys.todolistaproject.service.EventToTodoIService;
import com.skrys.todolistaproject.service.TodoLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
public class EventToTodoItemController {
    @Autowired
    private TodoLService todoLService;

    @Autowired
    private EventToTodoIService eventsToTodoItemsService;


    @Scheduled(cron = "*/1 * * * * *")
    public void convertEventsToTodoItems(){
        System.out.println("convertEventsToTodoItems");
        eventsToTodoItemsService.eventsToTodoItemsSave();
    }
}
