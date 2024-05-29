package com.springDevelopers.Backend.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class VerifyOtp {
    private Integer Otp;
    private String email;
}
