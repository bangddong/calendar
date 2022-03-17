package com.study.calendar.api.service;

import com.study.calendar.api.dto.AuthUser;
import com.study.calendar.api.dto.EngagementEmailStuff;
import com.study.calendar.api.dto.EventCreateReq;
import com.study.calendar.core.domain.RequestStatus;
import com.study.calendar.core.domain.entity.Engagement;
import com.study.calendar.core.domain.entity.Schedule;
import com.study.calendar.core.domain.entity.User;
import com.study.calendar.core.domain.entity.repository.EngagementRepository;
import com.study.calendar.core.domain.entity.repository.ScheduleRepository;
import com.study.calendar.core.exception.CalendarException;
import com.study.calendar.core.exception.ErrorCode;
import com.study.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EmailService emailService;
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    @Transactional
    public void create(EventCreateReq req, AuthUser authUser) {
        // attendees 의 스케쥴 시간과 겹치지 않는지?
        final List<Engagement> engagementList =
                engagementRepository.findAllByAttendeeIdInAndSchedule_EndAtAfter(req.getAttendeeIds(), req.getStartAt());
        if (engagementList.stream()
                .anyMatch(e -> e.getEvent().isOverlapped(req.getStartAt(), req.getEndAt())
                        && e.getStatus() == RequestStatus.ACCEPTED)) {
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }
        final Schedule eventSchedule = Schedule.event(
                req.getTitle(),
                req.getDescription(),
                req.getStartAt(),
                req.getEndAt(),
                userService.getOrThrowById(authUser.getId())
        );
        scheduleRepository.save(eventSchedule);

        final List<User> attendeeList = req.getAttendeeIds().stream().map(userService::getOrThrowById).collect(toList());
        attendeeList.forEach(user -> {
            final Engagement engagement = engagementRepository.save(new Engagement(eventSchedule, user));
            emailService.sendEngagement(
                    new EngagementEmailStuff(
                        engagement.getId(),
                        engagement.getAttendee().getEmail(),
                        attendeeList.stream().map(User::getEmail).collect(Collectors.toList()),
                        engagement.getEvent().getTile(),
                        engagement.getEvent().getPeriod()
            ));
        });
    }
}
