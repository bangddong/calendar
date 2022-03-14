package com.study.calendar.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate birthday;
    private LocalDateTime createAt;

    public User(String name, String email, String password, LocalDate birthday, LocalDateTime createAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.createAt = createAt;
    }
}
