package com.example.CampusActivityManagementsystem.dao;

import com.example.CampusActivityManagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // 后续功能拓展使用，暂时留空
}
