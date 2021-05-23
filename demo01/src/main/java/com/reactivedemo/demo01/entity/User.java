package com.reactivedemo.demo01.entity;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;

    @NotBlank
    private String name;

    private int age;
}
