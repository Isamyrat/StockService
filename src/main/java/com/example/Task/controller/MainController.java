package com.example.Task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value = "/")
    public String index() {
        return "main";
    }

    @GetMapping(value = "/main")
    public String main() {
        return "main";
    }
}
