package com.fqy.controller;

import com.fqy.pojo.User;
import com.fqy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Component111
public class UserController {

    @Autowired
    UserService userService ;

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

}
