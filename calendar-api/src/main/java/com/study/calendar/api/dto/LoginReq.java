package com.study.calendar.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReq {

    @NotBlank
    private final String email;

    @NotBlank
    private final String password;

}
