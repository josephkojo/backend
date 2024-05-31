package com.springDevelopers.Backend.Auth;

import com.springDevelopers.Backend.Services.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initAdmin() {
        if (!userService.adminExists()) {
            userService.registerAdmin();
        }
    }
}
