package com.holyw.holyw.controller;

import com.holyw.holyw.model.User;
import com.holyw.holyw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/maxId")
    public Integer maxId() {
        try {
            return userService.maxId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(value = "/get")
    public User get(Integer id) {
        try {
            return userService.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
