package com.study.calendar.api.service;

import com.study.calendar.api.dto.EngagementEmailStuff;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff engagement);
}
