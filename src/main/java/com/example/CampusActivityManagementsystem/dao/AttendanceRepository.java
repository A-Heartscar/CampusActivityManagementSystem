package com.example.CampusActivityManagementsystem.dao;

import com.example.CampusActivityManagementsystem.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    // 检查报名记录是否已关联签到（避免重复签到）
    boolean existsByRegistrationId(int registrationId);

    // 通过报名记录ID查询签到
    Attendance findByRegistrationId(int registrationId);

    // 联表查询：通过活动ID查询签到记录（关联activity_registration表）
    @Query("SELECT a FROM Attendance a JOIN ActivityRegistration ar ON a.registrationId = ar.id WHERE ar.activityId = :activityId")
    List<Attendance> findByActivityId(@Param("activityId") int activityId);

    // 联表查询：通过用户ID查询签到记录（关联activity_registration表）
    @Query("SELECT a FROM Attendance a JOIN ActivityRegistration ar ON a.registrationId = ar.id WHERE ar.userId = :userId")
    List<Attendance> findByUserId(@Param("userId") int userId);

    // 新增：统计指定活动的有效参与人数（已签到且未签退，即signOutTime为空）
    @Query("SELECT COUNT(a) FROM Attendance a " +
            "JOIN ActivityRegistration ar ON a.registrationId = ar.id " +
            "WHERE ar.activityId = :activityId " +
            "AND a.signInTime IS NOT NULL " + // 已签到
            "AND a.signOutTime IS NULL ")     // 未签退   // 排除已取消的报名
    int countValidParticipantsByActivityId(@Param("activityId") int activityId);

    // 新增：统计所有活动的总有效参与人数
    @Query("SELECT COUNT(a) FROM Attendance a " +
            "JOIN ActivityRegistration ar ON a.registrationId = ar.id " +
            "WHERE a.signInTime IS NOT NULL " +
            "AND a.signOutTime IS NULL ")
    int countTotalValidParticipants();
}
