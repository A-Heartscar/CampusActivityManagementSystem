package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.entity.Feedback;
import com.example.CampusActivityManagementsystem.service.FeedbackService;
import com.example.CampusActivityManagementsystem.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    // 新增评价反馈
    @PostMapping
    public Response<Feedback> addFeedback(@RequestBody Feedback feedback) {
        try {
            feedback.setId(0); // 确保ID自增
            return Response.newSuccess(feedbackService.addFeedback(feedback));
        } catch (Exception e) {
            return Response.newFail(feedback, e.getMessage());
        }
    }

    // 根据ID查询评价
    @GetMapping("/{id}")
    public Response<Feedback> getFeedbackById(@PathVariable int id) {
        try {
            return Response.newSuccess(feedbackService.getFeedbackById(id));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 查询所有评价
    @GetMapping("/getAll")
    public Response<List<Feedback>> getAllFeedbacks() {
        try {
            return Response.newSuccess(feedbackService.getAllFeedbacks());
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 按活动ID查询评价
    @GetMapping("/activity/{activityId}")
    public Response<List<Feedback>> getByActivityId(@PathVariable int activityId) {
        try {
            return Response.newSuccess(feedbackService.getFeedbacksByActivityId(activityId));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 按用户ID查询评价
    @GetMapping("/user/{userId}")
    public Response<List<Feedback>> getByUserId(@PathVariable int userId) {
        try {
            return Response.newSuccess(feedbackService.getFeedbacksByUserId(userId));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 更新评价反馈
    @PutMapping("/{id}")
    public Response<Feedback> updateFeedback(
            @PathVariable int id,
            @RequestBody Feedback feedback
    ) {
        try {
            return Response.newSuccess(feedbackService.updateFeedback(id, feedback));
        } catch (Exception e) {
            feedback.setId(id);
            return Response.newFail(feedback, e.getMessage());
        }
    }

    // 删除评价反馈
    @DeleteMapping("/{id}")
    public Response<Void> deleteFeedback(@PathVariable int id) {
        try {
            feedbackService.deleteFeedback(id);
            return Response.newSuccess(null);
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }
}
