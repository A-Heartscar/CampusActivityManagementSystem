package com.example.CampusActivityManagementsystem.dao;

import com.example.CampusActivityManagementsystem.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    // 按类型查询物资
    List<Material> findByMaterialType(String materialType);

    // 按名称模糊查询
    List<Material> findByNameContaining(String name);
}
