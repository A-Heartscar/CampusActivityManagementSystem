package com.example.CampusActivityManagementsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    // 暂时留空，以备拓展
}
