package com.example.CampusActivityManagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "material_usage")
public class MaterialUsage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "material_id", nullable = false)
    private int materialId; // 关联物资ID

    @Column(name = "activity_id", nullable = false)
    private int activityId; // 关联活动ID

    @Column(name = "user_id", nullable = false)
    private int userId; // 借用人ID

    @Column(name = "quantity")
    private int quantity; // 领用数量

    @Column(name = "borrow_time")
    private String borrowTime; // 领用时间（格式：yyyy-MM-dd HH:mm）

    @Column(name = "return_time")
    private String returnTime; // 归还时间,格式同上

    @Column(name = "status", nullable = false)
    private String status; // 状态（如：已领用/已归还/逾期）

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }
}
