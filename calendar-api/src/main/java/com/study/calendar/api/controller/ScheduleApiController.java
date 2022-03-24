package com.study.calendar.api.controller;

import com.study.calendar.api.dto.*;
import com.study.calendar.api.service.*;
import com.study.calendar.api.util.ApiUtils.ApiDataResponse;
import com.study.calendar.core.domain.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static com.study.calendar.api.util.ApiUtils.success;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleApiController {

    private final ScheduleQueryService scheduleQueryService;
    private final TaskService taskService;
    private final EventService eventService;
    private final EngagementService engagementService;
    private final NotificationService notificationService;

    @PostMapping("/tasks")
    public ApiDataResponse<Boolean> createTask(
            @Valid @RequestBody TaskCreateReq taskCreateReq,
            AuthUser authUser) {
        taskService.create(taskCreateReq, authUser);
        return success(true);
    }

    @PostMapping("/events")
    public ApiDataResponse<Boolean> createEvent(
            @Valid @RequestBody EventCreateReq eventCreateReq,
            AuthUser authUser) {
        eventService.create(eventCreateReq, authUser);
        return success(true);
    }

    @PostMapping("/notifications")
    public ApiDataResponse<Boolean> createNotifications(
            @Valid @RequestBody NotificationCreateReq notificationCreateReq,
            AuthUser authUser) {
        notificationService.create(notificationCreateReq, authUser);
        return success(true);
    }

    @GetMapping("/day")
    public ApiDataResponse<List<ScheduleDto>> getScheduleByDay(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return success(scheduleQueryService.getScheduleByDay(
                authUser, date == null ? LocalDate.now() : date
        ));
    }

    @GetMapping("/week")
    public ApiDataResponse<List<ScheduleDto>> getScheduleByWeek(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startOfWeek) {
        return success(scheduleQueryService.getScheduleByWeek(
                authUser, startOfWeek == null ? LocalDate.now() : startOfWeek
        ));
    }

    @GetMapping("/month")
    public ApiDataResponse<List<ScheduleDto>> getScheduleByMonth(
            AuthUser authUser,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM") String yearMonth) {
        return success(scheduleQueryService.getScheduleByMonth(
                authUser, yearMonth == null ? YearMonth.now() : YearMonth.parse(yearMonth)
        ));
    }

    @PutMapping("/events/engagements/{engagementId}")
    public ApiDataResponse<RequestStatus> updateEngagement(
            @Valid @RequestBody ReplyEngagementReq replyEngagementReq,
            @PathVariable Long engagementId,
            AuthUser authUser
    ) {
        return success(engagementService.update(authUser, engagementId, replyEngagementReq.getType()));
    }

}