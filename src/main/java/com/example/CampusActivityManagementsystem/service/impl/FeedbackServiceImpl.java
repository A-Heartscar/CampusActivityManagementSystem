package com.example.CampusActivityManagementsystem.service.impl;

import com.example.CampusActivityManagementsystem.entity.Feedback;
import com.example.CampusActivityManagementsystem.dao.FeedbackRepository;
import com.example.CampusActivityManagementsystem.dao.ActivityRepository;
import com.example.CampusActivityManagementsystem.dao.UserRepository;
import com.example.CampusActivityManagementsystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Feedback addFeedback(Feedback feedback) {
        try {
            // 校验活动存在性
            if (!activityRepository.existsById(feedback.getActivityId())) {
                throw new IllegalArgumentException("关联活动不存在，ID：" + feedback.getActivityId());
            }

            // 校验用户存在性
            if (!userRepository.existsById(feedback.getUserId())) {
                throw new IllegalArgumentException("关联用户不存在，ID：" + feedback.getUserId());
            }

            // 校验评分范围
            if (feedback.getRating() < 1 || feedback.getRating() > 5) {
                throw new IllegalArgumentException("评分必须在1-5之间");
            }

            // 设置提交时间（格式与项目保持一致）
            if (feedback.getCreateTime() == null) {
                feedback.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            } else {
                // 校验时间格式
                try {
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(feedback.getCreateTime());
                } catch (ParseException e) {
                    throw new IllegalArgumentException("时间格式错误，请使用\"yyyy-MM-dd HH:mm\"");
                }
            }

            return feedbackRepository.save(feedback);
        } catch (Exception e) {
            throw new RuntimeException("创建评价失败：" + e.getMessage());
        }
    }

    @Override
    public Feedback getFeedbackById(int id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("评价不存在，ID：" + id));
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        if (feedbacks.isEmpty()) {
            throw new RuntimeException("暂无评价记录");
        }
        return feedbacks;
    }

    @Override
    public List<Feedback> getFeedbacksByActivityId(int activityId) {
        List<Feedback> feedbacks = feedbackRepository.findByActivityId(activityId);
        if (feedbacks.isEmpty()) {
            throw new IllegalArgumentException("该活动无评价记录，活动ID：" + activityId);
        }
        return feedbacks;
    }

    @Override
    public List<Feedback> getFeedbacksByUserId(int userId) {
        List<Feedback> feedbacks = feedbackRepository.findByUserId(userId);
        if (feedbacks.isEmpty()) {
            throw new IllegalArgumentException("该用户无评价记录，用户ID：" + userId);
        }
        return feedbacks;
    }

    @Override
    public Feedback updateFeedback(int id, Feedback feedback) {
        try {
            Feedback existingFeedback = getFeedbackById(id);

            // 仅更新允许修改的字段（不允许修改关联ID）
            if (feedback.getRating() != 0) { // 评分不为默认值时更新
                if (feedback.getRating() < 1 || feedback.getRating() > 5) {
                    throw new IllegalArgumentException("评分必须在1-5之间");
                }
                existingFeedback.setRating(feedback.getRating());
            }
            if (feedback.getContent() != null) {
                existingFeedback.setContent(feedback.getContent());
            }
            existingFeedback.setAnonymous(feedback.getIsAnonymous());


            return feedbackRepository.save(existingFeedback);
        } catch (Exception e) {
            throw new RuntimeException("更新评价失败：" + e.getMessage());
        }
    }

    @Override
    public void deleteFeedback(int id) {
        if (!feedbackRepository.existsById(id)) {
            throw new RuntimeException("删除失败，评价不存在，ID：" + id);
        }
        feedbackRepository.deleteById(id);
    }
}
