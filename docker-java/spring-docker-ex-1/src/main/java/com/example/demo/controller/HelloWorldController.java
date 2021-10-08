package com.example.demo.controller;


import com.example.demo.service.HelloWorldService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class HelloWorldController {

    private HelloWorldService service;

    @GetMapping("/get")
    public String getStringFromService() {
        return service.getStringFromRepo();
    }
}
