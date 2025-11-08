package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.entity.ActivityStats;
import com.example.CampusActivityManagementsystem.entity.StatsSummary;

import java.util.List;

public interface StatisticsService {
    // 获取单个活动的统计数据
    ActivityStats getActivityStats(int activityId);

    // 获取所有活动的汇总统计
    StatsSummary getOverallStats();

    // 获取指定活动列表的统计数据（支持批量查询）
    List<ActivityStats> getActivityStatsList(List<Integer> activityIds);
}
