package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.entity.ActivityStats;
import com.example.CampusActivityManagementsystem.entity.StatsSummary;
import com.example.CampusActivityManagementsystem.service.Response;
import com.example.CampusActivityManagementsystem.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    // 获取单个活动的统计数据
    @GetMapping("/activity/{id}")
    public Response<ActivityStats> getActivityStats(@PathVariable int id) {
        try {
            return Response.newSuccess(statisticsService.getActivityStats(id));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 获取所有活动的汇总统计
    @GetMapping("/summary")
    public Response<StatsSummary> getOverallStats() {
        try {
            return Response.newSuccess(statisticsService.getOverallStats());
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 批量获取活动统计（按活动ID列表查询）
    @GetMapping("/activities")
    public Response<List<ActivityStats>> getActivityStatsList(@RequestParam List<Integer> ids) {
        try {
            return Response.newSuccess(statisticsService.getActivityStatsList(ids));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }
}
