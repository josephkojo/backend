package com.springDevelopers.Backend.Auth;

public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String schoolEmail;
    private String email;
    private String password;

    public RegisterRequest(){

    }

    public RegisterRequest(String firstname, String lastname, String schoolEmail, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.schoolEmail = schoolEmail;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
