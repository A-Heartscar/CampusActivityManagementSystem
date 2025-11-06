package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.dao.User;
import com.example.CampusActivityManagementsystem.service.Response;
import com.example.CampusActivityManagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/user/{id}")
    public Response<User> getUserById(@PathVariable int id) {
        return Response.newSuccess(userService.getUserById(id));
    }

    @PostMapping("/user/newUser")
    public Response<User> createUser(@RequestBody User user) {
        try {
            // 忽略用户传入的ID（如果有的话），确保由数据库生成
            user.setId(0); // 0值会被JPA忽略，使用自增策略
            User createdUser = userService.createUser(user);
            return Response.newSuccess(createdUser);
        } catch (RuntimeException e) {
            return Response.newFail(user, e.getMessage());
        }
    }

    // 新增更新用户接口
    @PutMapping("/user/update/{id}")
    public Response<User> updateUser(
            @PathVariable int id,
            @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return Response.newSuccess(updatedUser);
        } catch (RuntimeException e) {
            user.setId(id);
            return Response.newFail(user, e.getMessage());
        }
    }

    // 新增删除用户接口
    @DeleteMapping("/user/delete/{id}")
    public Response<Void> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUserById(id);
            return Response.newSuccess(null);
        } catch (RuntimeException e) {
            return Response.newFail(null, e.getMessage());
        }
    }
}
