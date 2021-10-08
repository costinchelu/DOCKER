package com.example.demo.service;

import com.example.demo.repository.HelloWorldRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HelloWorldService {

    HelloWorldRepo repo;

    public String getStringFromRepo() {
        return repo.getStringFromDb();
    }
}
