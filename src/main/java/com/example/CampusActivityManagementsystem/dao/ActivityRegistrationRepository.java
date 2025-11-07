package com.example.CampusActivityManagementsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRegistrationRepository extends JpaRepository<ActivityRegistration, Integer> {
    // 拓展方法：根据活动ID查询报名列表
    List<ActivityRegistration> findByActivityId(int activityId);

    // 拓展方法：根据用户ID查询报名列表
    List<ActivityRegistration> findByUserId(int userId);

    // 检查用户是否已报名该活动（避免重复报名）
    boolean existsByActivityIdAndUserId(int activityId, int userId);
}
