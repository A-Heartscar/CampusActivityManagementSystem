package com.example.CampusActivityManagementsystem.dao;

import com.example.CampusActivityManagementsystem.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    // 按活动ID查询评价
    List<Feedback> findByActivityId(int activityId);

    // 按用户ID查询评价
    List<Feedback> findByUserId(int userId);

    // 统计指定活动的评价总数
    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.activityId = :activityId")
    int countByActivityId(@Param("activityId") int activityId);

    // 计算指定活动的平均评分
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.activityId = :activityId")
    Double calculateAverageRating(@Param("activityId") int activityId);

    // 计算所有活动的整体平均评分
    @Query("SELECT AVG(f.rating) FROM Feedback f")
    Double calculateOverallAverageRating();
}
