package com.dnd.dndtravel.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("test")
    public String printTest() {
        return "CI/CD Success!";
    }
}