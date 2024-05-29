package com.springDevelopers.Backend.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.springDevelopers.Backend.Enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users_table")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String firstname;
    private String lastname;
    private String schoolEmail;
    private String email;

    public Set<ForgotPassword> getForgotPasswords() {
        return forgotPasswords;
    }

    public void setForgotPasswords(Set<ForgotPassword> forgotPasswords) {
        this.forgotPasswords = forgotPasswords;
    }

    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<AccessKey> accessKeys;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ForgotPassword> forgotPasswords;

    public User() { }

    public User(String firstname, String lastname, String schoolEmail, String email, Role role, String password, Set<AccessKey> accessKeys) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.schoolEmail = schoolEmail;
        this.email = email;
        this.role = role;
        this.password = password;
        this.accessKeys = accessKeys;
    }

    public User(Integer id, String email, Role role, String password) {
        Id = id;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public User(String firstname, String lastname, String schoolEmail, String email, Role role, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.schoolEmail = schoolEmail;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public Set<AccessKey> getAccessKeys() {
        return accessKeys;
    }

    public void setAccessKeys(Set<AccessKey> accessKeys) {
        this.accessKeys = accessKeys;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
