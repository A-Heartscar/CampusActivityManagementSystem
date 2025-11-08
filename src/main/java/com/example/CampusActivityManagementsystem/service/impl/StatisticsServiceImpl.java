package com.example.CampusActivityManagementsystem.service.impl;

import com.example.CampusActivityManagementsystem.dao.*;
import com.example.CampusActivityManagementsystem.entity.ActivityStats;
import com.example.CampusActivityManagementsystem.entity.StatsSummary;
import com.example.CampusActivityManagementsystem.entity.Activity;
import com.example.CampusActivityManagementsystem.entity.Material;
import com.example.CampusActivityManagementsystem.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private ActivityRepository activityRepository; // 用于查询活动名称
    @Autowired
    private ActivityRegistrationRepository registrationRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private MaterialUsageRepository materialUsageRepository;
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public ActivityStats getActivityStats(int activityId) {
        ActivityStats stats = new ActivityStats();
        stats.setActivityId(activityId);

        // 活动基本信息（标题）
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("活动不存在（ID：" + activityId + "）"));
        stats.setActivityTitle(activity.getTitle());

        // 报名人数
        stats.setRegistrationCount(registrationRepository.countByActivityId(activityId));

        // 参与人数
        stats.setParticipationCount(attendanceRepository.countValidParticipantsByActivityId(activityId));

        // 满意度统计
        ActivityStats.Satisfaction satisfaction = new ActivityStats.Satisfaction();
        satisfaction.setFeedbackCount(feedbackRepository.countByActivityId(activityId));
        Double avgRating = feedbackRepository.calculateAverageRating(activityId);
        satisfaction.setAverageRating(avgRating != null ? avgRating : 0.0);
        stats.setSatisfaction(satisfaction);

        // 物资使用统计
        ActivityStats.MaterialUsageSummary materialSummary = new ActivityStats.MaterialUsageSummary();
        List<Object[]> materialData = materialUsageRepository.sumQuantityByActivityId(activityId);
        List<ActivityStats.MaterialUsageSummary.MaterialUsageDetail> details = materialData.stream()
                .map(item -> {
                    int materialId = (int) item[0];
                    int totalQuantity = (int) item[1];
                    String materialName = materialRepository.findById(materialId)
                            .map(Material::getName)
                            .orElse("未知物资");
                    ActivityStats.MaterialUsageSummary.MaterialUsageDetail detail = new ActivityStats.MaterialUsageSummary.MaterialUsageDetail();
                    detail.setMaterialId(materialId);
                    detail.setMaterialName(materialName);
                    detail.setQuantity(totalQuantity);
                    return detail;
                })
                .collect(Collectors.toList());
        materialSummary.setTotalItems(details.size());
        materialSummary.setUsageDetails(details);
        stats.setMaterialUsage(materialSummary);

        return stats;
    }

    @Override
    public StatsSummary getOverallStats() {
        StatsSummary summary = new StatsSummary();
        // 总活动数
        summary.setTotalActivities((int) activityRepository.count());
        // 总报名人数
        summary.setTotalRegistrations(registrationRepository.countTotalValidRegistrations());
        // 总参与人数
        summary.setTotalParticipations(attendanceRepository.countTotalValidParticipants());
        // 整体平均满意度
        Double overallAvg = feedbackRepository.calculateOverallAverageRating();
        summary.setOverallSatisfaction(overallAvg != null ? overallAvg : 0.0);
        return summary;
    }

    @Override
    public List<ActivityStats> getActivityStatsList(List<Integer> activityIds) {
        return activityIds.stream()
                .map(this::getActivityStats)
                .collect(Collectors.toList());
    }
}
