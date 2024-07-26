package com.dnd.dndtravel.tset;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("test")
    public String printTest() {
        return "Success!";
    }
}