package com.holyw.holyw.controller;

import com.holyw.holyw.common.ResultEntity;
import com.holyw.holyw.model.User;
import com.holyw.holyw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResultEntity login(String userName, String password) {

        try {
            if(StringUtils.isEmpty(userName)) {
                //return "请输入用户名";
            }
            User user = userService.findByUserName(userName);
            if(Objects.isNull(user)) {
                //return "用户名不存在";
            }else if (StringUtils.isEmpty(password)){
                //return "请输入密码";
            }else {
                if(!password.equals(user.getPassword())) {
                    //return "密码错误";
                }else {
                    //return "SUCCESS";
                }
            }
            ArrayList<User> users = new ArrayList<>();
            users.add(user);
            users.add(new User());

            return ResultEntity.result("登录成功",users, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
