package com.holyw.holyw.controller;

import org.springframework.web.bind.annotation.GetMapping;


public class IndexController {

    @GetMapping(value = "/index")
    public String index() {

        return "SUCCESS";
    }
}
