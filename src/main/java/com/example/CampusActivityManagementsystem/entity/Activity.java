package com.example.CampusActivityManagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name="activity")
public class Activity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;// 活动主题

    @Column(name = "theme")
    private String theme;

    @Column (name = "start_time")
    private String startTime; // 开始时间（格式：yyyy-MM-dd HH:mm）

    @Column (name = "end_time")
    private String endTime; // 结束时间（格式：yyyy-MM-dd HH:mm）

    @Column(name = "location")
    private String location;// 地点

    @Column(name = "organizer")
    private String organizer; // 组织者

    @Column(name = "activity_content")
    private String activityContent; // 活动内容

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }
}
