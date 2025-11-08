package com.example.CampusActivityManagementsystem.service.impl;

import com.example.CampusActivityManagementsystem.dao.MaterialRepository;
import com.example.CampusActivityManagementsystem.dao.ActivityRepository;
import com.example.CampusActivityManagementsystem.dao.UserRepository;
import com.example.CampusActivityManagementsystem.dao.MaterialUsageRepository;
import com.example.CampusActivityManagementsystem.entity.Material;
import com.example.CampusActivityManagementsystem.entity.MaterialUsage;
import com.example.CampusActivityManagementsystem.service.MaterialUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MaterialUsageServiceImpl implements MaterialUsageService {
    @Autowired
    private MaterialUsageRepository materialUsageRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public MaterialUsage addMaterialUsage(MaterialUsage materialUsage) {
        // 验证关联数据存在性
        if (!materialRepository.existsById(materialUsage.getMaterialId())) {
            throw new IllegalArgumentException("物资不存在，ID：" + materialUsage.getMaterialId());
        }
        if (!activityRepository.existsById(materialUsage.getActivityId())) {
            throw new IllegalArgumentException("活动不存在，ID：" + materialUsage.getActivityId());
        }
        if (!userRepository.existsById(materialUsage.getUserId())) {
            throw new IllegalArgumentException("用户不存在，ID：" + materialUsage.getUserId());
        }

        // 验证领用数量
        if (materialUsage.getQuantity() <= 0) {
            throw new IllegalArgumentException("领用数量必须大于0");
        }

        // 验证时间格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (materialUsage.getBorrowTime() != null) {
            try {
                sdf.parse(materialUsage.getBorrowTime());
            } catch (java.text.ParseException e) {
                throw new IllegalArgumentException("领用时间格式错误，请使用\"yyyy-MM-dd HH:mm\"");
            }
        } else {
            // 默认使用当前时间
            materialUsage.setBorrowTime(sdf.format(new Date()));
        }
        return materialUsageRepository.save(materialUsage);
    }

    @Override
    public MaterialUsage getMaterialUsageById(int id) {
        return materialUsageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("领用记录不存在，ID：" + id));
    }

    @Override
    public List<MaterialUsage> getAllMaterialUsages() {
        List<MaterialUsage> usages = materialUsageRepository.findAll();
        if (usages.isEmpty()) {
            throw new RuntimeException("暂无领用记录");
        }
        return usages;
    }

    @Override
    public List<MaterialUsage> getByActivityId(int activityId) {
        List<MaterialUsage> usages = materialUsageRepository.findByActivityId(activityId);
        if (usages.isEmpty()) {
            throw new IllegalArgumentException("该活动无物资领用记录，活动ID：" + activityId);
        }
        return usages;
    }

    @Override
    public List<MaterialUsage> getByUserId(int userId) {
        List<MaterialUsage> usages = materialUsageRepository.findByUserId(userId);
        if (usages.isEmpty()) {
            throw new IllegalArgumentException("该用户无物资领用记录，用户ID：" + userId);
        }
        return usages;
    }

    @Override
    public List<MaterialUsage> getByMaterialId(int materialId) {
        List<MaterialUsage> usages = materialUsageRepository.findByMaterialId(materialId);
        if (usages.isEmpty()) {
            throw new IllegalArgumentException("该物资无领用记录，物资ID：" + materialId);
        }
        return usages;
    }

    @Override
    public MaterialUsage updateMaterialUsage(int id, MaterialUsage materialUsage) {
        try {
            // 查询原始领用记录
            MaterialUsage existingUsage = getMaterialUsageById(id);
            // 查询关联物资信息
            Material relatedMaterial = materialRepository.findById(existingUsage.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("关联物资不存在，ID：" + existingUsage.getMaterialId()));

            // 处理数量更新逻辑
            int newQuantity = materialUsage.getQuantity();
            if (newQuantity > 0) { // 仅处理有效数量更新
                int oldQuantity = existingUsage.getQuantity();
                int quantityDiff = newQuantity - oldQuantity;

                // 数量增加时校验可用库存
                if (quantityDiff > 0) {
                    if (quantityDiff > relatedMaterial.getAvailableQuantity()) {
                        throw new IllegalArgumentException("领用数量超出可用库存，当前可用：" + relatedMaterial.getAvailableQuantity() +
                                "，需增加：" + quantityDiff);
                    }
                    // 更新物资可用数量（减少）
                    relatedMaterial.setAvailableQuantity(relatedMaterial.getAvailableQuantity() - quantityDiff);
                }
                // 数量减少时直接更新（无需校验）
                else if (quantityDiff < 0) {
                    // 更新物资可用数量（增加）
                    relatedMaterial.setAvailableQuantity(relatedMaterial.getAvailableQuantity() + Math.abs(quantityDiff));
                }
                // 执行领用记录数量更新
                existingUsage.setQuantity(newQuantity);
                // 保存物资数量变更
                materialRepository.save(relatedMaterial);
            }

            // 处理时间更新逻辑
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            // 借用时间校验（非空时校验格式）
            if (materialUsage.getBorrowTime() != null) {
                try {
                    sdf.parse(materialUsage.getBorrowTime());
                    existingUsage.setBorrowTime(materialUsage.getBorrowTime());
                } catch (java.text.ParseException e) {
                    throw new IllegalArgumentException("借用时间格式错误，请使用\"yyyy-MM-dd HH:mm\"");
                }
            }

            // 归还时间校验（非空时校验格式和时间顺序）
            if (materialUsage.getReturnTime() != null) {
                try {
                    Date returnTime = sdf.parse(materialUsage.getReturnTime());
                    Date borrowTime = sdf.parse(existingUsage.getBorrowTime());

                    if (returnTime.before(borrowTime)) {
                        throw new IllegalArgumentException("归还时间不能早于借用时间");
                    }
                    existingUsage.setReturnTime(materialUsage.getReturnTime());
                } catch (java.text.ParseException e) {
                    throw new IllegalArgumentException("归还时间格式错误，请使用\"yyyy-MM-dd HH:mm\"");
                }
            }

            // 处理状态更新
            if (materialUsage.getStatus() != null && !materialUsage.getStatus().trim().isEmpty()) {
                existingUsage.setStatus(materialUsage.getStatus());
            }

            // 保存更新后的领用记录
            return materialUsageRepository.save(existingUsage);
        }catch (Exception e) {
            throw new RuntimeException("更新领用记录失败：" + e.getMessage());
        }
    }

    @Override
    public void deleteMaterialUsage(int id) {
        if (!materialUsageRepository.existsById(id)) {
            throw new RuntimeException("删除失败，领用记录不存在，ID：" + id);
        }
        materialUsageRepository.deleteById(id);
    }
}
