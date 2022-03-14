package com.study.calendar.api.controller;

import com.study.calendar.api.dto.AuthUser;
import com.study.calendar.api.dto.TaskCreateReq;
import com.study.calendar.api.service.controller.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.study.calendar.api.service.LoginService.LOGIN_SESSION_KEY;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleApiController {

    private final TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateReq taskCreateReq,
            HttpSession session) {
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        if (userId == null) {
            throw new RuntimeException("bad request. no session");
        }
        taskService.create(taskCreateReq, AuthUser.of(userId));
        return ResponseEntity.ok().build();
    }

}
