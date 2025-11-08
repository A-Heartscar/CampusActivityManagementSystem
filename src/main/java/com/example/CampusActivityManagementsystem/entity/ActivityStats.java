package com.example.CampusActivityManagementsystem.entity;


import lombok.Data;
import java.util.List;

@Data
public class ActivityStats {
    private int activityId;
    private String activityTitle; // 从activity表关联获取
    private int registrationCount; // 报名人数
    private int participationCount; // 参与人数
    private Satisfaction satisfaction; // 满意度信息
    private MaterialUsageSummary materialUsage; // 物资使用汇总

    // 内部类：满意度详情
    @Data
    public static class Satisfaction {
        private double averageRating; // 平均评分
        private int feedbackCount; // 评价总数
    }

    // 内部类：物资使用汇总
    @Data
    public static class MaterialUsageSummary {
        private int totalItems; // 物资种类数
        private List<MaterialUsageDetail> usageDetails; // 具体物资使用情况

        @Data
        public static class MaterialUsageDetail {
            private int materialId;
            private String materialName; // 从material表关联获取
            private int quantity; // 领用总量
        }
    }
}

