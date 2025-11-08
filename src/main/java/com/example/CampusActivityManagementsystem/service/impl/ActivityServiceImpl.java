package com.example.CampusActivityManagementsystem.service.impl;

import com.example.CampusActivityManagementsystem.entity.Activity;
import com.example.CampusActivityManagementsystem.dao.ActivityRepository;
import com.example.CampusActivityManagementsystem.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public Activity addActivity(Activity activity) {
        try{
            if (activity.getTitle() == null || activity.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("活动标题不能为空");
            }

            // 时间不为空时判断
            if (activity.getStartTime() != null && activity.getEndTime() != null) {
                int result = 0;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                try{
                    Date startTime = sdf.parse(activity.getStartTime());
                    Date endTime = sdf.parse(activity.getEndTime());
                    result = startTime.compareTo(endTime);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("时间格式错误：\"yyyy-MM-dd HH:mm\"");
                }
                if(result >= 0){
                    throw new IllegalArgumentException("活动开始时间不能大于等于结束时间");
                }

            }
            return activityRepository.save(activity);
        }catch (Exception e){
            throw new RuntimeException("创建活动失败原因: "+ e.getMessage());
        }

    }

    @Override
    public Activity getActivityById(int id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("活动不存在，ID：" + id));
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity updateActivity(int id, Activity activity) {
        // 先查询再更新（与用户实体修改逻辑一致）
        Activity existingActivity = getActivityById(id);

        // 仅更新非空字段（避免覆盖原有值）
        if (activity.getTitle() != null) {
            existingActivity.setTitle(activity.getTitle());
        }
        if (activity.getTheme() != null) {
            existingActivity.setTheme(activity.getTheme());
        }
        if (activity.getStartTime() != null) {
            existingActivity.setStartTime(activity.getStartTime());
        }
        if (activity.getEndTime() != null) {
            existingActivity.setEndTime(activity.getEndTime());
        }
        if (activity.getLocation() != null) {
            existingActivity.setLocation(activity.getLocation());
        }
        if (activity.getOrganizer() != null) {
            existingActivity.setOrganizer(activity.getOrganizer());
        }
        if (activity.getActivityContent() != null) {
            existingActivity.setActivityContent(activity.getActivityContent());
        }

        return activityRepository.save(existingActivity);
    }

    @Override
    public void deleteActivity(int id) {
        if (!activityRepository.existsById(id)) {
            throw new RuntimeException("删除失败，活动不存在，ID：" + id);
        }
        activityRepository.deleteById(id);
    }
}
