package com.study.calendar.api.service.controller;

import com.study.calendar.api.dto.AuthUser;
import com.study.calendar.api.dto.TaskCreateReq;
import com.study.calendar.core.domain.entity.Schedule;
import com.study.calendar.core.domain.entity.repository.ScheduleRepository;
import com.study.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    public void create(TaskCreateReq taskCreateReq, AuthUser authUser) {
        final Schedule taskSchedule =
                Schedule.task(
                        taskCreateReq.getTitle(),
                        taskCreateReq.getDescription(),
                        taskCreateReq.getTaskAt(),
                        userService.findByUserId(authUser.getId())
                        );
        scheduleRepository.save(taskSchedule);
    }

}
