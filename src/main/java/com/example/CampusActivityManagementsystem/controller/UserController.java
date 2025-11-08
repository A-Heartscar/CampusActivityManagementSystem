package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.entity.User;
import com.example.CampusActivityManagementsystem.service.Response;
import com.example.CampusActivityManagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 根据id查用户
    @GetMapping("/user/{id}")
    public Response<User> getUserById(@PathVariable int id) {
        return Response.newSuccess(userService.getUserById(id));
    }

    // 创建新用户
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

    // 更新用户
    @PutMapping("/user/{id}")
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

    // 删除用户
    @DeleteMapping("/user/{id}")
    public Response<Void> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUserById(id);
            return Response.newSuccess(null);
        } catch (RuntimeException e) {
            return Response.newFail(null, e.getMessage());
        }
    }
}
