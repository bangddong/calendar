package com.study.calendar.api.service;

import com.study.calendar.api.dto.EngagementEmailStuff;
import com.study.calendar.core.domain.entity.Engagement;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff engagement);
}
