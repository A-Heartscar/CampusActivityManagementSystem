package com.example.CampusActivityManagementsystem.dao;

import com.example.CampusActivityManagementsystem.entity.ActivityRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // 统计指定活动的报名人数（过滤无效状态，如"已取消"）
    @Query("SELECT COUNT(r) FROM ActivityRegistration r WHERE r.activityId = :activityId AND r.status != 'CANCELLED'")
    int countByActivityId(@Param("activityId") int activityId);

    // 统计所有活动的总报名人数
    @Query("SELECT COUNT(r) FROM ActivityRegistration r WHERE r.status != 'CANCELLED'")
    int countTotalValidRegistrations();
}
