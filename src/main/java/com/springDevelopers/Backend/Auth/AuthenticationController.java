package com.springDevelopers.Backend.Auth;

import com.springDevelopers.Backend.DTO.MailBody;
import com.springDevelopers.Backend.Entities.User;
import com.springDevelopers.Backend.Enums.Role;
import com.springDevelopers.Backend.Repositories.UserRepository;
import com.springDevelopers.Backend.Services.EmailService;
import com.springDevelopers.Backend.Services.UserService;
import com.springDevelopers.Backend.SpringSecurity.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

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
        MailBody mailBody = new MailBody();
        mailBody.setRecipient(registerRequest.getEmail());
        mailBody.setText("Congratulations Dear "+ registerRequest.getFirstname() + " your have been successfully  registered to" +
                "the Access key Manager Platform ");
        mailBody.setSubject("Access Key Manager");
        emailService.simpleMailSender(mailBody);



        AuthenticateResponse authenticateResponse = this.authenticationService.registerUser(registerRequest);
        return new ResponseEntity<>(authenticateResponse, HttpStatus.CREATED);

    }
    @PostMapping("/login")
    @CrossOrigin(origins ="https://accesskey.onrender.com")
    public ResponseEntity<AuthenticateResponse> loginUser(@RequestBody AuthenticateRequest authenticateRequest){


        AuthenticateResponse authenticateResponse = this.authenticationService.loginUser(authenticateRequest);
        return new ResponseEntity<>(authenticateResponse, HttpStatus.OK);
   }
    @GetMapping("/users")
    public ResponseEntity<List<User>> generateUser(){
        return new ResponseEntity<>(this.userRepository.findAll(), HttpStatus.OK);
    }

}
