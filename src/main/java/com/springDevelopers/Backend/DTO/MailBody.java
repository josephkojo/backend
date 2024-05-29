package com.springDevelopers.Backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailBody {
    private String recipient;
    private String subject;
    private String text;
}
