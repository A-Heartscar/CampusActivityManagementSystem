package com.example.CampusActivityManagementsystem.dao;

import com.example.CampusActivityManagementsystem.entity.MaterialUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialUsageRepository extends JpaRepository<MaterialUsage, Integer> {
    // 按活动ID查询领用记录
    List<MaterialUsage> findByActivityId(int activityId);

    // 按用户ID查询领用记录
    List<MaterialUsage> findByUserId(int userId);

    // 按物资ID查询领用记录
    List<MaterialUsage> findByMaterialId(int materialId);
    // 按活动ID分组统计物资领用总量
    @Query("SELECT m.materialId, SUM(m.quantity) FROM MaterialUsage m WHERE m.activityId = :activityId GROUP BY m.materialId")
    List<Object[]> sumQuantityByActivityId(@Param("activityId") int activityId);
}
