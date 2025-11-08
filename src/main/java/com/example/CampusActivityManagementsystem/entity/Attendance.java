package com.example.CampusActivityManagementsystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "registration_id", nullable = false)
    private int registrationId; // 关联活动ID，外键


    @Column(name = "sign_in_time")
    private String signInTime;

    @Column(name = "sign_out_time")
    private String signOutTime;

    @Column(name = "status")
    private String status;


    public int getId() {
        return id;
    }

    public int getRegistrationId() {
        return registrationId;
    }


    public String getSignInTime() {
        return signInTime;
    }

    public String getSignOutTime() {
        return signOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }


    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }

    public void setSignOutTime(String signOutTime) {
        this.signOutTime = signOutTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
