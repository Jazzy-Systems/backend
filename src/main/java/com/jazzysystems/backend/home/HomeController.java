package com.jazzysystems.backend.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home() {
        return "Hello, World!";
    }

    @GetMapping(path = "/user")
    public String user() {
        return "hello, user";
    }

    @GetMapping(path = "/admin")
    public String admin() {
        return "hello, admin";
    }
}
