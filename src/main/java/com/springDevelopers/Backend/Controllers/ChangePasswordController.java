package com.springDevelopers.Backend.Controllers;

import com.springDevelopers.Backend.DTO.ChangePassword;
import com.springDevelopers.Backend.DTO.MailBody;
import com.springDevelopers.Backend.DTO.VerifyOtp;
import com.springDevelopers.Backend.Entities.ForgotPassword;
import com.springDevelopers.Backend.Entities.User;
import com.springDevelopers.Backend.Repositories.ForgotPasswordRepository;
import com.springDevelopers.Backend.Repositories.UserRepository;
import com.springDevelopers.Backend.Services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forgotPassword")
public class ChangePasswordController {
    private final PasswordEncoder passwordEncoder;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @PostMapping("/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email){
        try{
            User user = userRepository.findByEmail(email).orElseThrow(() ->
                    new UsernameNotFoundException("user email does not exist"));
            if(user == null){
                return ResponseEntity.notFound().build();
            }
            Integer code = generateVerificationCode();
            MailBody mailBody =  new MailBody();
            mailBody.setRecipient(email);
            mailBody.setText("Please enter this verification code to reset Account "+ code +
                    "<br><br> <strong>visit this link to reset password </strong> https://accesskey.onrender.com/forgotPassword");
            mailBody.setSubject("ForgotPassword");
            ForgotPassword forgotPassword = new ForgotPassword();
            forgotPassword.setOtp(code);
            forgotPassword.setExpirationDate(new Date(System.currentTimeMillis() + 70000 * 1000));
            forgotPassword.setUser(user);
            emailService.simpleMailSender(mailBody);
            this.forgotPasswordRepository.save(forgotPassword);

            return new ResponseEntity<>("Verification has been successfully sent", HttpStatus.OK);
        }catch (Exception exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());

        }

    }

    @PostMapping("/verify")
    public ResponseEntity<String> validateConfirmationCode(@RequestBody VerifyOtp verifyOtp){
        User user = userRepository.findByEmail(verifyOtp.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("user email does not exist"));
        List<ForgotPassword> forgotPasswords = this.forgotPasswordRepository.findForgotPasswordGeneratedByUser(user);
        Integer currentOtp = forgotPasswords.get(forgotPasswords.size() - 1).getOtp();
        if(!verifyOtp.getOtp().equals(currentOtp)){
            return new ResponseEntity<>("Please take the currently otp" + currentOtp, HttpStatus.BAD_REQUEST);
        }
        ForgotPassword forgotPassword = this.forgotPasswordRepository
                .findConfirmationCodeAndEmail(currentOtp, user).orElseThrow(()-> new RuntimeException("Could not find"));
        if(forgotPassword.getExpirationDate().before(Date.from(Instant.now()))){
            this.forgotPasswordRepository.deleteById(forgotPassword.getForgotPasswordId());
            return new ResponseEntity<>("Sorry otp has already expired", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Opt has been successfully verified", HttpStatus.OK);

    }


    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword){
        if(!changePassword.getPassword().equals(changePassword.getRepeatPassword())){
            return new ResponseEntity<>("Password does not match", HttpStatus.EXPECTATION_FAILED);
        }
        User user = userRepository.findByEmail(changePassword.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String newPassword = passwordEncoder.encode(changePassword.getPassword());
        user.setPassword(newPassword);
        this.userRepository.save(user);
        return new ResponseEntity<>("Your password is " + changePassword.getPassword(), HttpStatus.OK);
    }






    private Integer generateVerificationCode(){
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }


}
