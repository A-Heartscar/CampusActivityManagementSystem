package com.example.CampusActivityManagementsystem.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "activity_registration")
public class ActivityRegistration {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "activity_id")
    private int activityId; // 关联活动ID

    @Column(name = "user_id")
    private int userId; // 关联用户ID

    @Column(name = "reason")
    private String reason;  // 报名原因

    @Column(name = "status")
    private String status;  // 报名状态

    @Column(name = "registration_time")
    private String registrationTime; //报名时间（格式：yyyy-MM-dd HH:mm）

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }




}
