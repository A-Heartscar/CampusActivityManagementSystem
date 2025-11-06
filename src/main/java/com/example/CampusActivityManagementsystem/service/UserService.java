package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.dao.User;

public interface UserService {
    User getUserById(int id);
    User createUser(User user);
    User updateUser(int id, User user);
    void deleteUserById(int id);
}
