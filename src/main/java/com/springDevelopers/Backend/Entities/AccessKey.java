package com.springDevelopers.Backend.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.springDevelopers.Backend.Enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "access_key")
public class AccessKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String keyName;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private LocalDate dateOfProcurement;
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public AccessKey() { }

    public AccessKey(String keyName, Status status, LocalDate dateOfProcurement, LocalDate expiryDate, User user) {
        this.keyName = keyName;
        this.status = status;
        this.dateOfProcurement = dateOfProcurement;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
