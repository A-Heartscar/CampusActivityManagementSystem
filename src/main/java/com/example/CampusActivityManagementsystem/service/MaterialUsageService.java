package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.entity.MaterialUsage;
import java.util.List;

public interface MaterialUsageService {

    // 新增领用记录
    MaterialUsage addMaterialUsage(MaterialUsage materialUsage);

    // 根据ID查询领用记录
    MaterialUsage getMaterialUsageById(int id);

    // 查询所有领用记录
    List<MaterialUsage> getAllMaterialUsages();

    // 按活动ID查询领用记录
    List<MaterialUsage> getByActivityId(int activityId);

    // 按用户ID查询领用记录
    List<MaterialUsage> getByUserId(int userId);

    // 按物资ID查询领用记录
    List<MaterialUsage> getByMaterialId(int materialId);

    // 更新领用记录
    MaterialUsage updateMaterialUsage(int id, MaterialUsage materialUsage);

    // 删除领用记录
    void deleteMaterialUsage(int id);
}
