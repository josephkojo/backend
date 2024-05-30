package com.springDevelopers.Backend.Auth;

import com.springDevelopers.Backend.Entities.User;
import com.springDevelopers.Backend.Repositories.UserRepository;
import com.springDevelopers.Backend.Services.UserService;
import com.springDevelopers.Backend.SpringSecurity.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
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
//        if(authenticateRequest.getEmail().equals("mosesmensah081@gmail.com") && authenticateRequest.getPassword().equals("ebo")){
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(),
//                    authenticateRequest.getPassword()));
//            User user = this.userRepository.findByEmail(authenticateRequest.getEmail()).orElseThrow();
//            String token = this.jwtService.generateToken(user);
//            AuthenticateResponse authenticateResponse = new AuthenticateResponse();
//            authenticateResponse.setId(user.getId());
//            authenticateResponse.setFirstname(user.getFirstname());
//            authenticateResponse.setEmail(user.getEmail());
//            authenticateResponse.setRole(user.getRole().toString());
//            authenticateResponse.setToken(token);
//            return new ResponseEntity<>(authenticateResponse, HttpStatus.OK);
//        }
        AuthenticateResponse authenticateResponse = this.authenticationService.loginUser(authenticateRequest);
        return new ResponseEntity<>(authenticateResponse, HttpStatus.OK);
   }

}
