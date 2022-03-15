package com.study.calendar.api.service;

import com.study.calendar.core.domain.entity.Engagement;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class FakeEmailService implements EmailService{

    @Override
    public void sendEngagement(Engagement engagement) {
        System.out.println(
                "send email. email : "
                + engagement.getAttendee().getEmail()
                + ", scheduleId : "
                + engagement.getEvent().getId());
    }
}
