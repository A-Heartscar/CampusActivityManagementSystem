package com.example.CampusActivityManagementsystem.service.impl;

import com.example.CampusActivityManagementsystem.entity.Material;
import com.example.CampusActivityManagementsystem.dao.MaterialRepository;
import com.example.CampusActivityManagementsystem.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Material addMaterial(Material material) {
        material.setId(0);

        // 校验必填字段
        if (material.getName() == null || material.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("物资名称不能为空");
        }
        // 校验数量合理性（非负）
        if (material.getTotalQuantity() < 0) {
            throw new IllegalArgumentException("总数量不能为负数");
        }
        if (material.getAvailableQuantity() < 0) {
            throw new IllegalArgumentException("可用数量不能为负数");
        }
        // 可用数量不能超过总数量
        if (material.getAvailableQuantity() > material.getTotalQuantity()) {
            throw new IllegalArgumentException("可用数量不能超过总数量");
        }

        return materialRepository.save(material);
    }

    @Override
    public Material getMaterialById(int id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("物资不存在，ID：" + id));
    }

    @Override
    public List<Material> getAllMaterials() {
        List<Material> materials = materialRepository.findAll();
        if(!materials.isEmpty()) {
            return materials;
        }else {
            throw new RuntimeException("无任何物资");
        }
    }

    @Override
    public List<Material> getMaterialsByType(String materialType) {
        List<Material> materials = materialRepository.findByMaterialType(materialType);
        if (materials.isEmpty()) {
            throw new RuntimeException("未找到类型为{" + materialType + "}的物资");
        }
        return materials;
    }

    @Override
    public List<Material> getMaterialsByName(String name) {
        List<Material> materials = materialRepository.findByNameContaining(name);
        if (materials.isEmpty()) {
            throw new RuntimeException("未找到名称包含{" + name + "}的物资");
        }
        return materials;
    }

    @Override
    public Material updateMaterial(int id, Material material) {
        // 查询原物资
        Material existing = getMaterialById(id);

        // 更新非空字段
        if (material.getName() != null && !material.getName().trim().isEmpty()) {
            existing.setName(material.getName());
        }
        if (material.getMaterialType() != null) {
            existing.setMaterialType(material.getMaterialType());
        }
        // 数量更新需校验合理性
        if (material.getTotalQuantity() >= 0) {// 新总数量不为负
            existing.setTotalQuantity(material.getTotalQuantity());

        }
        if (material.getAvailableQuantity() >= 0) {
            // 可用数量不能超过总数量
            if (material.getAvailableQuantity() > existing.getTotalQuantity()) {
                throw new IllegalArgumentException("可用数量不能超过总数量");
            }
            existing.setAvailableQuantity(material.getAvailableQuantity());
        }
        if (material.getUnit() != null) {
            existing.setUnit(material.getUnit());
        }
        if (material.getStorageLocation() != null) {
            existing.setStorageLocation(material.getStorageLocation());
        }

        return materialRepository.save(existing);
    }

    @Override
    public void deleteMaterial(int id) {
        if (!materialRepository.existsById(id)) {
            throw new RuntimeException("删除失败，物资不存在，ID：" + id);
        }
        materialRepository.deleteById(id);
    }
}
