package com.study.calendar.api.controller;

import com.study.calendar.api.dto.*;
import com.study.calendar.api.service.EventService;
import com.study.calendar.api.service.NotificationService;
import com.study.calendar.api.service.ScheduleQueryService;
import com.study.calendar.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleApiController {

    private final ScheduleQueryService scheduleQueryService;
    private final TaskService taskService;
    private final EventService eventService;
    private final NotificationService notificationService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(
            @RequestBody TaskCreateReq taskCreateReq,
            AuthUser authUser) {
        taskService.create(taskCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/events")
    public ResponseEntity<Void> createEvent(
            @RequestBody EventCreateReq eventCreateReq,
            AuthUser authUser) {
        eventService.create(eventCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notifications")
    public ResponseEntity<Void> createNotifications(
            @RequestBody NotificationCreateReq notificationCreateReq,
            AuthUser authUser) {
        notificationService.create(notificationCreateReq, authUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/day")
    public List<ScheduleDto> getScheduleByDay(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return scheduleQueryService.getScheduleByDay(authUser, date == null ? LocalDate.now() : date);
    }

}