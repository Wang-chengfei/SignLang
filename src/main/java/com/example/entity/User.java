package com.example.entity;

import lombok.Data;
import javax.persistence.Table;

@Data
@Table(name = "user")
public class User {

    private Integer id;

    private String nick;

    private String openid;

    private String sessionKey;

    private String imgUrl;

    private Character sex;
}
