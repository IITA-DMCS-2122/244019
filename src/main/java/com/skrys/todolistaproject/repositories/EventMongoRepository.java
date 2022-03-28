package com.skrys.todolistaproject.repositories;


import com.skrys.todolistaproject.entity.mgdb.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface EventMongoRepository extends MongoRepository<Event, Long> {


    List<Event> findAllByType(String type);
    List<Event> findAllByStatus(String status);
    List<Event> findAllByTimestamp(Timestamp timestamp);
    void deleteById(String Id);
    Event findTopByOrderByTimestampDesc();

    List<Event> findByTimestampGreaterThanEqualOrderByTimestampAsc(long timestamp);
    Event findByTodoItemBusinessKey(String businessKey);

    List<Event> findAll();


}