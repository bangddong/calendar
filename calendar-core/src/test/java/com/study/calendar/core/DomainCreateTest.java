package com.study.calendar.core;

import com.study.calendar.core.domain.Engagement;
import com.study.calendar.core.domain.Event;
import com.study.calendar.core.domain.RequestStatus;
import com.study.calendar.core.domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @Test
    void eventCreate() {
        final User writer = new User("writer", "email@email", "pw", LocalDate.now(), LocalDateTime.now());
        final User attendee = new User("attendee", "email@email", "pw", LocalDate.now(), LocalDateTime.now());
        final Event event = new Event(
                LocalDateTime.now(),
                LocalDateTime.now(),
                "title", "desc",
                writer,
                LocalDateTime.now()
        );

        event.addEngagement(new Engagement(event, attendee, LocalDateTime.now(), RequestStatus.REQUESTED));
        assertEquals(event.getEngagements().get(0).getEvent().getWriter().getName(), "writer");
    }
}
