package com.skrys.todolistaproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class TodoL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int priority;

    @Column(name = "topic", unique = true)
    private String topic;
    private String status;
    private String description;
    private String username;

}
