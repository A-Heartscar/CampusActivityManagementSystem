package com.example.CampusActivityManagementsystem.entity;

import lombok.Data;

// 全局汇总实体
@Data
public class StatsSummary {
    private int totalActivities; // 活动总数
    private int totalRegistrations; // 总报名人数
    private int totalParticipations; // 总参与人数
    private double overallSatisfaction; // 整体平均满意度
}
