package com.example.demo.repository;

import org.springframework.stereotype.Repository;

@Repository
public class HelloWorldRepo {

    public String getStringFromDb() {
        return "Hello World";
    }
}
