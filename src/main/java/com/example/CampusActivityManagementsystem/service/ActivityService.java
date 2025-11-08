package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.entity.Activity;

import java.util.List;

public interface ActivityService {
    // 新增活动
    Activity addActivity(Activity activity);

    // 根据ID查询活动
    Activity getActivityById(int id);

    // 查询所有活动
    List<Activity> getAllActivities();

    // 修改活动
    Activity updateActivity(int id, Activity activity);

    // 删除活动
    void deleteActivity(int id);
}
