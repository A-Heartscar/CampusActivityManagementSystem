package com.example.CampusActivityManagementsystem.service;


import com.example.CampusActivityManagementsystem.dao.ActivityRegistration;

import java.util.List;

public interface ActivityRegistrationService {
    // 新增报名
    ActivityRegistration register(ActivityRegistration registration);

    // 按ID查询报名
    ActivityRegistration getRegistrationById(int id);

    // 查询所有报名
    List<ActivityRegistration> getAllRegistrations();

    // 按活动ID查询报名
    List<ActivityRegistration> getRegistrationsByActivityId(int activityId);

    // 按用户ID查询报名
    List<ActivityRegistration> getRegistrationsByUserId(int userId);

    // 更新报名状态
    ActivityRegistration updateRegistration(int id, ActivityRegistration registration);

    // 删除报名
    void deleteRegistration(int id);
}
