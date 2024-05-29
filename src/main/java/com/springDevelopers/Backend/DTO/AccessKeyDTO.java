package com.springDevelopers.Backend.DTO;

import com.springDevelopers.Backend.Enums.Status;

import java.time.LocalDate;

public class AccessKeyDTO {
    private Integer Id;
    private String key;
    private Status status;
    private LocalDate dateOfProcurement;
    private LocalDate expiryDate;

    private String schoolEmail;
    public AccessKeyDTO(){

    }

    public AccessKeyDTO(Integer id, String key, Status status, LocalDate dateOfProcurement, LocalDate expiryDate, String schoolEmail) {
        Id = id;
        this.key = key;
        this.status = status;
        this.dateOfProcurement = dateOfProcurement;
        this.expiryDate = expiryDate;
        this.schoolEmail = schoolEmail;
    }


    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getDateOfProcurement() {
        return dateOfProcurement;
    }

    public void setDateOfProcurement(LocalDate dateOfProcurement) {
        this.dateOfProcurement = dateOfProcurement;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
