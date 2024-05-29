package com.springDevelopers.Backend.Services;

import com.springDevelopers.Backend.DTO.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final String SENDER_EMAIL = "kojoaidoi@gmail.com";
    private final JavaMailSender javaMailSender;

    public void simpleMailSender(MailBody mailBody){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailBody.getRecipient());
        simpleMailMessage.setFrom(SENDER_EMAIL);
        simpleMailMessage.setSubject(mailBody.getSubject());
        simpleMailMessage.setText(mailBody.getText());
        javaMailSender.send(simpleMailMessage);
    }

}
