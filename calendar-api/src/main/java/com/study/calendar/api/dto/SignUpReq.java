package com.study.calendar.api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpReq {
    private final String name;
    private final String email;
    private final String password;
    private final LocalDate birthday;
}
