package com.skrys.todolistaproject.service;

import com.skrys.todolistaproject.entity.mgdb.Event;
import com.skrys.todolistaproject.entity.pg.TodoL;
import com.skrys.todolistaproject.repositories.EventMongoRepository;
import com.skrys.todolistaproject.repositories.TodoLElasticRepository;
import com.skrys.todolistaproject.repositories.pg1.TodoLPg1Repository;
import com.skrys.todolistaproject.repositories.pg2.TodoLPg2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventToTodoIService {


    @Autowired
    private TodoLPg1Repository todoLRepository;
    @Autowired
    private TodoLPg2Repository todoLPg2Repository;
    @Autowired
    private EventMongoRepository eventMongoRepository;
    @Autowired
    private TodoLElasticRepository todoLElasticRepository;

    @Autowired
    private TodoLService todoService;

    public EventToTodoIService(TodoLPg1Repository todoLRepository, TodoLPg2Repository todoLPg2Repository, EventMongoRepository eventMongoRepository, TodoLElasticRepository todoLElasticRepository, TodoLService todoService) {
        this.todoLRepository = todoLRepository;
        this.todoLPg2Repository = todoLPg2Repository;
        this.eventMongoRepository = eventMongoRepository;
        this.todoLElasticRepository = todoLElasticRepository;
        this.todoService = todoService;
    }


    public long countEvents(){
        return eventMongoRepository.count();
    }

    public List<Event> getEvents(){
        return eventMongoRepository.findAll();
    }


    public void eventsToTodoItemsSave(){
        System.out.println("eventsToTodoItemsSave");
        TodoL todo = todoLRepository.findTopByOrderByIdDesc();

        List<Event> eventList;

        if(todo!= null){
            //System.out.println("todo!=null");
            Event event = eventMongoRepository.findByTodoItemBusinessKey(
                    todo.getBusinessKey());
            //System.out.println("Bkey: "+todo.getBusinessKey());
            if(event!= null) {
                //System.out.println("event!=null");
                eventList = eventMongoRepository.findByTimestampGreaterThanEqualOrderByTimestampAsc(event.getTimestamp());

                if(eventList.size()>1) {
                    System.out.println("-!-!-!-Events to TodoItems update");
                    for (Event tmpEv : eventList) {
                        if (!tmpEv.getTodoItem().getBusinessKey().equals(todo.getBusinessKey())) {
                            //^nie jest ostatnim zapisanym elementem w bazie
                            TodoL tmpTodoL = todoLRepository.save(tmpEv.getTodoItem());
                            todoLPg2Repository.save(tmpTodoL);//zapisywanie do analitics
                            todoLElasticRepository.save(todoService.pgTodoLtoElasticTodoLElastic(tmpTodoL));
                        }
                    }
                }

            }else{//brak ostatniego eventu - pusty Mongo
                //nic nie rób
                return;
            }

        }else{//brak todoItem - pusta baza
            //przepisz wszystko z mongo
            eventList = eventMongoRepository.findAll();
            for (Event tmpEv: eventList) {
                    TodoL tmpTodoL = todoLRepository.save(tmpEv.getTodoItem());
                    todoLPg2Repository.save(tmpTodoL);//zapisywanie do analitics
                    todoLElasticRepository.save(todoService.pgTodoLtoElasticTodoLElastic(tmpTodoL));
            }
        }







    }

}
