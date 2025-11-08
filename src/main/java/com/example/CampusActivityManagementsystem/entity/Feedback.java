package com.example.CampusActivityManagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "activity_id", nullable = false)
    private int activityId; // 关联活动ID

    @Column(name = "user_id", nullable = false)
    private int userId; // 关联用户ID

    @Column(name = "rating", nullable = false)
    private int rating; // 评分（1-5）

    @Column(name = "content")
    private String content; // 反馈内容

    @Column(name = "create_time")
    private String createTime; // 提交时间（格式：yyyy-MM-dd HH:mm）

    @Column(name = "is_anonymous")
    private boolean isAnonymous = false; // 是否匿名，默认值为false

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }
}
