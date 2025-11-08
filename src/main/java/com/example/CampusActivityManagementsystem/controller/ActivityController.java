package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.entity.Activity;
import com.example.CampusActivityManagementsystem.service.ActivityService;
import com.example.CampusActivityManagementsystem.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    ActivityService activityService;

    // 新增活动
    @PostMapping("/activity")
    public Response<Activity> addActivity(@RequestBody Activity activity) {
        try {
            activity.setId(0);
            return Response.newSuccess(activityService.addActivity(activity));
        }catch (Exception e){
            return Response.newFail(activity, e.getMessage());
        }
    }

    // 根据ID查询活动
    @GetMapping("/activity/{id}")
    public Response<Activity> getActivity(@PathVariable Integer id) {
        try{
            return Response.newSuccess(activityService.getActivityById(id));
        }catch (Exception e){
            return Response.newFail(null, e.getMessage());
        }
    }

    // 查询所有活动
    @GetMapping("/activity/getAll")
    public Response<List<Activity>> getAllActivities() {
        return Response.newSuccess(activityService.getAllActivities());
    }

    // 修改活动
    @PutMapping("/activity/{id}")
    public Response<Activity> updateActivity(
            @PathVariable Integer id,
            @RequestBody Activity activity
    ) {
        try{
            return Response.newSuccess(activityService.updateActivity(id, activity));
        }catch (Exception e){
            return Response.newFail(activity, e.getMessage());
        }
    }

    // 删除活动
    @DeleteMapping("/activity/{id}")
    public Response<Void> deleteActivity(@PathVariable Integer id) {
        try{
            activityService.deleteActivity(id);
            return Response.newSuccess(null);
        }catch (Exception e){
            return Response.newFail(null, e.getMessage());
        }
    }
}
