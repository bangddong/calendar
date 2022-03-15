package com.study.calendar.api.service;

import com.study.calendar.core.domain.entity.Engagement;

public interface EmailService {
    void sendEngagement(Engagement engagement);
}
