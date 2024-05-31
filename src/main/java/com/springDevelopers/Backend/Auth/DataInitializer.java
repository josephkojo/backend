package com.springDevelopers.Backend.Auth;

import com.springDevelopers.Backend.Entities.User;
import com.springDevelopers.Backend.Enums.Role;
import com.springDevelopers.Backend.Repositories.UserRepository;
import com.springDevelopers.Backend.SpringSecurity.JwtService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostConstruct
    public void registerAdmin(){
        User user = new User();
        user.setFirstname("Isaac");
        user.setLastname("Asante");
        user.setSchoolEmail("bompeh@gmail.com");
        user.setEmail("mosesmensah081@gmail.com");
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode("ebo"));
        this.userRepository.save(user);
        String token = jwtService.generateToken(user);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        authenticateResponse.setId(user.getId());
        authenticateResponse.setFirstname(user.getFirstname());
        authenticateResponse.setEmail(user.getEmail());
        authenticateResponse.setRole(user.getRole().toString());
        authenticateResponse.setToken(token);
    }
}
