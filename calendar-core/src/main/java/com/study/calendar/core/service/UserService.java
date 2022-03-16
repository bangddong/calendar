package com.study.calendar.core.service;

import com.study.calendar.core.domain.entity.User;
import com.study.calendar.core.domain.entity.repository.UserRepository;
import com.study.calendar.core.dto.UserCreateReq;
import com.study.calendar.core.exception.CalendarException;
import com.study.calendar.core.exception.ErrorCode;
import com.study.calendar.core.util.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Encryptor encryptor;
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateReq userCreateReq) {
        userRepository.findByEmail(userCreateReq.getEmail())
                .ifPresent(u -> {
                    throw new CalendarException(ErrorCode.ALREADY_EXISTS_USER);
                });
        return userRepository.save(new User(
                userCreateReq.getName(),
                userCreateReq.getEmail(),
                encryptor.encrypt(userCreateReq.getPassword()),
                userCreateReq.getBirthday()
        ));
    }

    @Transactional
    public Optional<User> findPwMatchUser(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.isMatch(encryptor, password) ? user : null);
    }

    public User getOrThrowById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CalendarException(ErrorCode.USER_NOT_FOUND));
    }
}
