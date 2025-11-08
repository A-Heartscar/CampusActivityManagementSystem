package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    // 新增评价反馈
    Feedback addFeedback(Feedback feedback);

    // 根据ID查询评价
    Feedback getFeedbackById(int id);

    // 查询所有评价
    List<Feedback> getAllFeedbacks();

    // 按活动ID查询评价
    List<Feedback> getFeedbacksByActivityId(int activityId);

    // 按用户ID查询评价
    List<Feedback> getFeedbacksByUserId(int userId);

    // 更新评价反馈
    Feedback updateFeedback(int id, Feedback feedback);

    // 删除评价反馈
    void deleteFeedback(int id);
}
