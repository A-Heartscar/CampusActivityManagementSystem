package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.dao.User;
import com.example.CampusActivityManagementsystem.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
