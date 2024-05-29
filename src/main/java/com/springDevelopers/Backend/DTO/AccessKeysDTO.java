package com.springDevelopers.Backend.DTO;

import lombok.Setter;

import java.time.LocalDate;


public class AccessKeysDTO {
    private  Integer Id;
    private String key;
    private String status;
    private LocalDate dateOfProcurement;
    private LocalDate expiryDate;

    public AccessKeysDTO(){

    }

    public AccessKeysDTO(Integer id, String key, String status, LocalDate dateOfProcurement, LocalDate expiryDate) {
        Id = id;
        this.key = key;
        this.status = status;
        this.dateOfProcurement = dateOfProcurement;
        this.expiryDate = expiryDate;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
