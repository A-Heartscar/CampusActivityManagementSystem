package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.entity.ActivityRegistration;
import com.example.CampusActivityManagementsystem.service.ActivityRegistrationService;
import com.example.CampusActivityManagementsystem.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityRegistrationController {
    @Autowired
    private ActivityRegistrationService activityRegistrationService;

    @PostMapping("/activity_register")
    public Response<ActivityRegistration> register(@RequestBody ActivityRegistration registration) {
        try {
            registration.setId(0);  // 确保ID自增
            return Response.newSuccess(activityRegistrationService.register(registration));
        } catch (Exception e) {
            return Response.newFail(registration, e.getMessage());
        }
    }


    @GetMapping("/activity_register/{id}")
    public Response<ActivityRegistration> getActivityRegistrationById(@PathVariable int id) {
        try{
            return Response.newSuccess(activityRegistrationService.getRegistrationById(id));
        }catch (Exception e){
            return Response.newFail(null, e.getMessage());
        }
    }

    // 查询所有报名
    @GetMapping("/activity_register/getAll")
    public Response<List<ActivityRegistration>> getAllActivityRegistration() {
        try {
            return Response.newSuccess(activityRegistrationService.getAllRegistrations());
        }catch (Exception e){
            return Response.newFail(null, e.getMessage());
        }
    }

    // 根据活动ID查询报名
    @GetMapping("/activity_register/activityId/{activityId}")
    public Response<List<ActivityRegistration>> getSignUpsByActivity(@PathVariable int activityId) {
        try {
            return Response.newSuccess(activityRegistrationService.getRegistrationsByActivityId(activityId));
        }catch (Exception e){
            return Response.newFail(null, e.getMessage());
        }
    }

    // 根据用户ID查询报名
    @GetMapping("/activity_register/userId/{userId}")
    public Response<List<ActivityRegistration>> getSignUpsByUser(@PathVariable Integer userId) {
        try {
            return Response.newSuccess(activityRegistrationService.getRegistrationsByUserId(userId));
        }catch (Exception e){
            return Response.newFail(null, e.getMessage());
        }
    }


    // 修改报名
    @PutMapping("/activity_register/{id}")
    public Response<ActivityRegistration> updateRegistration(@PathVariable int id, @RequestBody ActivityRegistration registration) {
        try {
            return Response.newSuccess(activityRegistrationService.updateRegistration(id, registration));
        } catch (Exception e) {
            registration.setId(id);
            return Response.newFail(registration, e.getMessage());
        }
    }

    // 删除报名
    @DeleteMapping("/activity_register/{id}")
    public Response<Void> deleteSignUp(@PathVariable int id) {
        try {
            activityRegistrationService.deleteRegistration(id);
            return Response.newSuccess(null);
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }
}
