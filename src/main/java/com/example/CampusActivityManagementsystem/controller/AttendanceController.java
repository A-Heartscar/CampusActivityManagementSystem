package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.entity.Attendance;
import com.example.CampusActivityManagementsystem.service.AttendanceService;
import com.example.CampusActivityManagementsystem.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendances")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    // 新增签到
    @PostMapping
    public Response<Attendance> addAttendance(@RequestBody Attendance attendance) {
        try {
            attendance.setId(0);
            return Response.newSuccess(attendanceService.addAttendance(attendance));
        } catch (Exception e) {
            return Response.newFail(attendance, e.getMessage());
        }
    }

    // 根据ID查询签到
    @GetMapping("/{id}")
    public Response<Attendance> getAttendanceById(@PathVariable int id) {
        try {
            return Response.newSuccess(attendanceService.getAttendanceById(id));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 查询所有签到
    @GetMapping("/getAll")
    public Response<List<Attendance>> getAllAttendances() {
        try {
            return Response.newSuccess(attendanceService.getAllAttendances());
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 根据报名ID查询签到
    @GetMapping("/registration/{registrationId}")
    public Response<Attendance> getByRegistrationId(@PathVariable int registrationId) {
        try {
            return Response.newSuccess(attendanceService.getAttendanceByRegistrationId(registrationId));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 根据活动ID查询签到（联表查询）
    @GetMapping("/activity/{activityId}")
    public Response<List<Attendance>> getByActivityId(@PathVariable int activityId) {
        try {
            return Response.newSuccess(attendanceService.getAttendancesByActivityId(activityId));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 根据用户ID查询签到（联表查询）
    @GetMapping("/user/{userId}")
    public Response<List<Attendance>> getByUserId(@PathVariable int userId) {
        try {
            return Response.newSuccess(attendanceService.getAttendancesByUserId(userId));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 更新签到/签退信息
    @PutMapping("/{id}")
    public Response<Attendance> updateAttendance(
            @PathVariable int id,
            @RequestBody Attendance attendance
    ) {
        try {
            return Response.newSuccess(attendanceService.updateAttendance(id, attendance));
        } catch (Exception e) {
            attendance.setId(id);
            return Response.newFail(attendance, e.getMessage());
        }
    }

    // 删除签到
    @DeleteMapping("/{id}")
    public Response<Void> deleteAttendance(@PathVariable int id) {
        try {
            attendanceService.deleteAttendance(id);
            return Response.newSuccess(null);
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }
}
