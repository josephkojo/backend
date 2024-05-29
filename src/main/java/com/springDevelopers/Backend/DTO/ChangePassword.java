package com.springDevelopers.Backend.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ChangePassword {
    private String email;
    private String password;
    private String repeatPassword;
}
