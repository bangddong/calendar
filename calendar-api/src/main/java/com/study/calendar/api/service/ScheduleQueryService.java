package com.study.calendar.api.service;

import com.study.calendar.api.dto.AuthUser;
import com.study.calendar.api.dto.ScheduleDto;
import com.study.calendar.api.util.DtoConverter;
import com.study.calendar.core.domain.entity.repository.EngagementRepository;
import com.study.calendar.core.domain.entity.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleQueryService {

    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ScheduleDto> getScheduleByDay(AuthUser authUser, LocalDate date) {
        return Stream.concat(
                scheduleRepository.findAllByWriter_Id(authUser.getId())
                        .stream()
                        .filter(schedule -> schedule.isOverlapped(date))
                        .map(DtoConverter::fromSchedule),
                engagementRepository.findAllByAttendee_Id(authUser.getId())
                        .stream()
                        .filter(engagement -> engagement.isOverlapped(date))
                        .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule()))
        ).collect(Collectors.toList());
    }
}
