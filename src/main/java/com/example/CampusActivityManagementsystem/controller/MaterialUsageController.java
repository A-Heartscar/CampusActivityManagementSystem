package com.example.CampusActivityManagementsystem.controller;

import com.example.CampusActivityManagementsystem.entity.MaterialUsage;
import com.example.CampusActivityManagementsystem.service.MaterialUsageService;
import com.example.CampusActivityManagementsystem.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material-usages")
public class MaterialUsageController {
    @Autowired
    private MaterialUsageService materialUsageService;

    // 新增领用记录
    @PostMapping
    public Response<MaterialUsage> addMaterialUsage(@RequestBody MaterialUsage materialUsage) {
        try {
            materialUsage.setId(0); // 确保ID自增
            return Response.newSuccess(materialUsageService.addMaterialUsage(materialUsage));
        } catch (Exception e) {
            return Response.newFail(materialUsage, e.getMessage());
        }
    }

    // 根据ID查询领用记录
    @GetMapping("/{id}")
    public Response<MaterialUsage> getMaterialUsageById(@PathVariable int id) {
        try {
            return Response.newSuccess(materialUsageService.getMaterialUsageById(id));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 查询所有领用记录
    @GetMapping("/getAll")
    public Response<List<MaterialUsage>> getAllMaterialUsages() {
        try {
            return Response.newSuccess(materialUsageService.getAllMaterialUsages());
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 按活动ID查询领用记录
    @GetMapping("/activity/{activityId}")
    public Response<List<MaterialUsage>> getByActivityId(@PathVariable int activityId) {
        try {
            return Response.newSuccess(materialUsageService.getByActivityId(activityId));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 按用户ID查询领用记录
    @GetMapping("/user/{userId}")
    public Response<List<MaterialUsage>> getByUserId(@PathVariable int userId) {
        try {
            return Response.newSuccess(materialUsageService.getByUserId(userId));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 按物资ID查询领用记录
    @GetMapping("/material/{materialId}")
    public Response<List<MaterialUsage>> getByMaterialId(@PathVariable int materialId) {
        try {
            return Response.newSuccess(materialUsageService.getByMaterialId(materialId));
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }

    // 更新领用记录
    @PutMapping("/{id}")
    public Response<MaterialUsage> updateMaterialUsage(
            @PathVariable int id,
            @RequestBody MaterialUsage materialUsage
    ) {
        try {
            return Response.newSuccess(materialUsageService.updateMaterialUsage(id, materialUsage));
        } catch (Exception e) {
            materialUsage.setId(id);
            return Response.newFail(materialUsage, e.getMessage());
        }
    }

    // 删除领用记录
    @DeleteMapping("/{id}")
    public Response<Void> deleteMaterialUsage(@PathVariable int id) {
        try {
            materialUsageService.deleteMaterialUsage(id);
            return Response.newSuccess(null);
        } catch (Exception e) {
            return Response.newFail(null, e.getMessage());
        }
    }
}
