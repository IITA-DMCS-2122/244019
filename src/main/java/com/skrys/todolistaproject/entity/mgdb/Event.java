package com.skrys.todolistaproject.entity.mgdb;

import com.skrys.todolistaproject.entity.pg.TodoL;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document("event")
public class Event {

/*
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
 */

    @Id
    private String Id;

    private String type;
    private TodoL todoItem;
    private String status;


    private long timestamp;

    public Event(String id, String type, TodoL todoItem, String status, long timestamp) {
        Id = id;
        this.type = type;
        this.todoItem = todoItem;
        this.status = status;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @PrePersist
    protected void onCreate() {
        setId(java.util.UUID.randomUUID().toString());
    }


    /*
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TodoL getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(TodoL todoItem) {
        this.todoItem = todoItem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
