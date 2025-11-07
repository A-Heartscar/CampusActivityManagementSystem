package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.dao.ActivityRegistration;
import com.example.CampusActivityManagementsystem.dao.ActivityRegistrationRepository;
import com.example.CampusActivityManagementsystem.dao.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ActivityRegistrationServiceImpl implements ActivityRegistrationService {
    @Autowired
    ActivityRegistrationRepository activityRegistrationRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public ActivityRegistration register(ActivityRegistration registration) {
        try {
            // 校验活动id有效性，后续可以修改为根据活动名称查找活动
            if(!activityRepository.existsById(registration.getActivityId())) {
                throw new IllegalArgumentException("活动不存在，ID: " + registration.getActivityId());
            }

            // 校验重复报名
            if(activityRegistrationRepository.existsByActivityIdAndUserId(registration.getActivityId(), registration.getUserId())) {
                throw new IllegalArgumentException("用户已报名该活动");
            }

            // 校验时间格式
            if (registration.getRegistrationTime() != null) {
                try {
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(registration.getRegistrationTime());
                } catch (ParseException e) {
                    throw new IllegalArgumentException("报名时间格式错误，请使用\"yyyy-MM-dd HH:mm\"");
                }
            }else {// 没有时间则默认当前时间
                registration.setRegistrationTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            }

            return activityRegistrationRepository.save(registration);
        }catch (Exception e){
            throw new RuntimeException("报名失败原因:" + e.getMessage());
        }
    }

    @Override
    public ActivityRegistration getRegistrationById(int id) {
        return activityRegistrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("报名记录不存在，ID：" + id));
    }

    @Override
    public List<ActivityRegistration> getAllRegistrations() {
        List<ActivityRegistration> registrations = activityRegistrationRepository.findAll();
        if (!registrations.isEmpty()) {
            return registrations;
        }else {
            throw new RuntimeException("结果为空");
        }

    }

    @Override
    public List<ActivityRegistration> getRegistrationsByActivityId(int activityId) {
        List<ActivityRegistration> registrations = activityRegistrationRepository.findByActivityId(activityId);
        if (!registrations.isEmpty()) {
            return registrations;
        }else {
            throw new IllegalArgumentException("该活动id（"+ activityId +"）无报名记录");
        }
    }

    @Override
    public List<ActivityRegistration> getRegistrationsByUserId(int userId) {
        List<ActivityRegistration> registrations = activityRegistrationRepository.findByUserId(userId);
        if (!registrations.isEmpty()) {
            return registrations;
        }else {
            throw new IllegalArgumentException("该用户id（"+ userId +"）无报名记录");
        }
    }

    @Override
    public ActivityRegistration updateRegistration(int id, ActivityRegistration registration) {
        try {
            // 查询已有记录
            ActivityRegistration existingRegistration = getRegistrationById(id);

            // 仅更新允许修改的字段（不允许修改活动ID和用户ID）
            if (registration.getReason() != null) {
                existingRegistration.setReason(registration.getReason());
            }
            if (registration.getStatus() != null) {
                existingRegistration.setStatus(registration.getStatus());
            }
            // 报名时间通常不允许修改，若有特殊需求可放开，后续可在下方添加代码

            return activityRegistrationRepository.save(existingRegistration);
        }catch (Exception e){
            throw new RuntimeException("更新失败:" + e.getMessage());
        }
    }

    @Override
    public void deleteRegistration(int id) {
        if (!activityRegistrationRepository.existsById(id)) {
            throw new RuntimeException("删除失败，报名记录不存在，ID：" + id);
        }
        activityRegistrationRepository.deleteById(id);
    }


}
