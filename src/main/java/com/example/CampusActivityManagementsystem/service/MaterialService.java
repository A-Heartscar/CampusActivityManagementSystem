package com.example.CampusActivityManagementsystem.service;

import com.example.CampusActivityManagementsystem.entity.Material;
import java.util.List;

public interface MaterialService {
    // 新增物资
    Material addMaterial(Material material);

    // 根据ID查询物资
    Material getMaterialById(int id);

    // 查询所有物资
    List<Material> getAllMaterials();

    // 按类型查询物资
    List<Material> getMaterialsByType(String materialType);

    // 按名称模糊查询
    List<Material> getMaterialsByName(String name);

    // 更新物资信息
    Material updateMaterial(int id, Material material);

    // 删除物资
    void deleteMaterial(int id);
}
