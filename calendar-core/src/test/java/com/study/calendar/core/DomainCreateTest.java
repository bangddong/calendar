package com.study.calendar.core;

import com.study.calendar.core.domain.ScheduleType;
import com.study.calendar.core.domain.entity.Schedule;
import com.study.calendar.core.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @Test
    void eventCreate() {
        final User me = new User("meme", "email", "pw", LocalDate.now());
        final Schedule taskScheduler = Schedule.task("할일", "청소하기", LocalDateTime.now(), me);
        assertEquals(taskScheduler.getScheduleType(), ScheduleType.TASK);
        assertEquals(taskScheduler.toTask().getTitle(), "할일");
    }
}
