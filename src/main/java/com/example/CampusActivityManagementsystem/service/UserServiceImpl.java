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
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("用户不存在，ID: " + id));
    }

    @Override
    public User createUser(User user) {
        try {
            // 处理name为空的情况，设置默认值
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                user.setName("defaultName");
            }
            // 保存用户，ID由数据库自动生成
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("创建用户失败: " + e.getMessage());
        }
    }

    @Override
    public User updateUser(int id, User user) {
        // 查找数据库中已存在的用户
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 更新字段（仅更新非空的传入字段）
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        // 保存更新后的用户信息
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUserById(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("用户不存在，ID: " + id);
        }

        userRepository.deleteById(id);
    }


}
