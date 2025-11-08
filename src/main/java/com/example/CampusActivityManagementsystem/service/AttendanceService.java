package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.entity.Attendance;

import java.util.List;

public interface AttendanceService {
    // 新增签到（需验证报名记录存在）
    Attendance addAttendance(Attendance attendance);

    // 根据ID查询签到
    Attendance getAttendanceById(int id);

    // 查询所有签到
    List<Attendance> getAllAttendances();

    // 根据报名记录ID查询签到
    Attendance getAttendanceByRegistrationId(int registrationId);

    // 根据活动ID查询签到（联表查询）
    List<Attendance> getAttendancesByActivityId(int activityId);

    // 根据用户ID查询签到（联表查询）
    List<Attendance> getAttendancesByUserId(int userId);

    // 更新签到/签退信息
    Attendance updateAttendance(int id, Attendance attendance);

    // 删除签到
    void deleteAttendance(int id);
}
