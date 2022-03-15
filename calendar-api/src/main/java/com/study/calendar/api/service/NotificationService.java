package com.study.calendar.api.service;

import com.study.calendar.api.dto.AuthUser;
import com.study.calendar.api.dto.NotificationCreateReq;
import com.study.calendar.core.domain.entity.Schedule;
import com.study.calendar.core.domain.entity.User;
import com.study.calendar.core.domain.entity.repository.ScheduleRepository;
import com.study.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(NotificationCreateReq notificationCreateReq, AuthUser authUser) {
        final User user = userService.getOrThrowById(authUser.getId());
        final List<LocalDateTime> notifyAtList = notificationCreateReq.getRepeatTimes();
        notifyAtList.forEach(notifyAt -> {
            final Schedule notificationSchedule =
                    Schedule.notification(
                            notificationCreateReq.getTitle(),
                            notifyAt,
                            user
                    );
            scheduleRepository.save(notificationSchedule);
        });
    }
}
