package com.study.calendar.api.service;

import com.study.calendar.api.dto.AuthUser;
import com.study.calendar.core.domain.RequestReplyType;
import com.study.calendar.core.domain.RequestStatus;
import com.study.calendar.core.domain.entity.repository.EngagementRepository;
import com.study.calendar.core.exception.CalendarException;
import com.study.calendar.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EngagementService {

    private final EngagementRepository engagementRepository;

    @Transactional
    public RequestStatus update(AuthUser authUser, Long engagementId, RequestReplyType type) {
        return engagementRepository.findById(engagementId)
                .filter(e -> e.getStatus() == RequestStatus.REQUESTED)
                .filter(e -> e.getAttendee().getId().equals(authUser.getId()))
                .map(e -> e.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST))
                .getStatus();
    }
}
