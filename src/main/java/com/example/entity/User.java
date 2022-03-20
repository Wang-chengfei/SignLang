package com.example.entity;

import lombok.Data;
import javax.persistence.Table;

@Data
@Table(name = "user")
public class User {

    private Long id;

    private String name;

    private int age;

    private String email;
}
