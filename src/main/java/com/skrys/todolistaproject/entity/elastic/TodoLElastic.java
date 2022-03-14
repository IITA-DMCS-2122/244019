package com.skrys.todolistaproject.entity.elastic;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

@Document(indexName = "todo")
public class TodoLElastic {

    public TodoLElastic(long id, int priority, String topic, String status, String description, String username, String businessKey) {
        this.id = id;
        this.priority = priority;
        this.topic = topic;
        this.status = status;
        this.description = description;
        this.username = username;
        this.businessKey = businessKey;
    }

    @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private int priority;

        @Column(name = "topic", unique = true)
        private String topic;
        private String status;
        private String description;
        private String username;
        @Column(unique = true)
        private String businessKey;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getBusinessKey() {
            return businessKey;
        }

        public void setBusinessKey(String businessKey) {
            this.businessKey = businessKey;
        }


}
