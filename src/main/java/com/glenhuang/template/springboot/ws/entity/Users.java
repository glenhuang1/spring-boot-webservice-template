package com.glenhuang.template.springboot.ws.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// for Mongo DB usage

@Document(collection="users")
@Data
public class Users {

    @Id
    private String id;

    private String firstname;

    private String lastname;


}
