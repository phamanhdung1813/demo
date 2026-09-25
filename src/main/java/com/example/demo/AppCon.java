package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class AppCon {
    @Autowired
    private AppService appService;

    @GetMapping("/test")
    public String test() {
        appService.methodA();
        return "OK";
    }

    @GetMapping("/all")
    public Iterable<AppEntity> getAll() {
        return appService.getAll();
    }
}
