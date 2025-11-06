package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.dao.User;
import com.example.CampusActivityManagementsystem.service.Response;
import com.example.CampusActivityManagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/user/{id}")
    public Response<User> getUserById(@PathVariable int id) {
        return Response.newSuccess(userService.getUserById(id));
    }

}
