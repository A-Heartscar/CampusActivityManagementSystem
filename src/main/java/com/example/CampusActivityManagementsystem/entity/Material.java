package com.example.CampusActivityManagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "material")
public class Material {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name; // 物资名称（非空）

    @Column(name = "material_type") // 原type改为m_type，避免关键字冲突
    private String materialType; // 类型（如：设备/耗材）

    @Column(name = "total_quantity")
    private int totalQuantity; // 总数量

    @Column(name = "available_quantity")
    private int availableQuantity; // 可用数量

    @Column(name = "unit")
    private String unit; // 单位（如：个/件/套）

    @Column(name = "storage_location", columnDefinition = "TEXT")
    private String storageLocation; // 存放位置（数据库用TEXT类型）

    // Getter和Setter方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }
}
