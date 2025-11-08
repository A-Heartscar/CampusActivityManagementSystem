package com.example.CampusActivityManagementsystem.service.impl;

import com.example.CampusActivityManagementsystem.dao.*;
import com.example.CampusActivityManagementsystem.entity.Attendance;
import com.example.CampusActivityManagementsystem.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private ActivityRegistrationRepository registrationRepository; // 用于验证报名记录存在


    @Override
    public Attendance addAttendance(Attendance attendance) {

        int registrationId = attendance.getRegistrationId();

        // 验证报名记录是否存在
        if (registrationId <= 0 || !registrationRepository.existsById(registrationId)) {
            throw new IllegalArgumentException("报名记录不存在，无法签到（报名ID：" + registrationId + "）");
        }

        // 验证是否已签到（避免重复签到）
        if (attendanceRepository.existsByRegistrationId(registrationId)) {
            throw new IllegalArgumentException("该报名已签到，不可重复签到（报名ID：" + registrationId + "）");
        }

        // 校验签到时间
        if(attendance.getSignInTime() != null) {
            try {
                new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(attendance.getSignInTime());
            }catch (java.text.ParseException e) {
                throw new IllegalArgumentException("签到时间格式错误，请使用\"yyyy-MM-dd HH:mm\"");
            }
        }else {
            attendance.setSignInTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        }

        // 创建时默认不能签退
        attendance.setSignOutTime(null);

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance getAttendanceById(int id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("签到记录不存在（ID：" + id + "）"));
    }

    @Override
    public List<Attendance> getAllAttendances() {
        List<Attendance> attendances = attendanceRepository.findAll();
        if (!attendances.isEmpty()) {
            return attendances;
        }else {
            throw new RuntimeException("结果为空");
        }

    }

    @Override
    public Attendance getAttendanceByRegistrationId(int registrationId) {
        Attendance attendance = attendanceRepository.findByRegistrationId(registrationId);
        if (attendance == null) {
            throw new RuntimeException("该报名尚未开始签到（报名ID：" + registrationId + "）");
        }
        return attendance;
    }

    @Override
    public List<Attendance> getAttendancesByActivityId(int activityId) {
        List<Attendance> attendances = attendanceRepository.findByActivityId(activityId);
        if (attendances.isEmpty()) {
            throw new RuntimeException("该活动暂无签到记录（活动ID：" + activityId + "）");
        }
        return attendances;
    }

    @Override
    public List<Attendance> getAttendancesByUserId(int userId) {
        List<Attendance> attendances = attendanceRepository.findByUserId(userId);
        if (attendances.isEmpty()) {
            throw new RuntimeException("该用户暂无签到记录（用户ID：" + userId + "）");
        }
        return attendances;
    }

    @Override
    public Attendance updateAttendance(int id, Attendance attendance) {
        // 查询原签到记录
        Attendance existing = getAttendanceById(id);

        // 不允许修改关联的报名记录ID（registration_id）

        // 更新可修改字段
        if(attendance.getSignInTime() != null) {
            try {
                new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(attendance.getSignInTime());
            }catch (ParseException e) {
                throw new IllegalArgumentException("签到时间格式错误，请使用\"yyyy-MM-dd HH:mm\"");
            }
        }else {
            existing.setSignInTime(attendance.getSignInTime());
        }

        if (attendance.getSignOutTime() != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date signOut = sdf.parse(attendance.getSignOutTime());
                Date signIn = sdf.parse(existing.getSignInTime());

                if (signOut.before(signIn)) {
                    throw new IllegalArgumentException("签退时间不能早于签到时间");
                }
                existing.setSignOutTime(attendance.getSignOutTime());
            } catch (ParseException e) {
                throw new IllegalArgumentException("签退时间格式错误，请使用\"yyyy-MM-dd HH:mm\"");
            }
        }else {
            existing.setSignOutTime(attendance.getSignOutTime());
        }

        // 后续若对签到状态进行额外限制在下边添加代码

        return attendanceRepository.save(existing);
    }

    @Override
    public void deleteAttendance(int id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("删除失败，签到记录不存在（ID：" + id + "）");
        }
        attendanceRepository.deleteById(id);
    }
}
