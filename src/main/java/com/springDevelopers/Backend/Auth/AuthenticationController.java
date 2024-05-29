package com.springDevelopers.Backend.Auth;

import com.springDevelopers.Backend.Entities.User;
import com.springDevelopers.Backend.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        List<User> findAllUser = this.userService.findAllUser();
        for(User user: findAllUser){
            if(user.getEmail().equals(registerRequest.getEmail())){
                return new ResponseEntity<>("User Already exist", HttpStatus.BAD_REQUEST);
            }

        }
        AuthenticateResponse authenticateResponse = this.authenticationService.registerUser(registerRequest);
        return new ResponseEntity<>(authenticateResponse, HttpStatus.CREATED);

    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> loginUser(@RequestBody AuthenticateRequest authenticateRequest){
        AuthenticateResponse authenticateResponse = this.authenticationService.loginUser(authenticateRequest);
        return new ResponseEntity<>(authenticateResponse, HttpStatus.OK);
    }
}
