package com.study.calendar.core.domain;

import com.study.calendar.core.domain.entity.Schedule;
import com.study.calendar.core.domain.entity.User;
import com.study.calendar.core.util.Period;

import java.time.LocalDateTime;

public class Event {

    private final Schedule schedule;

    public Event(Schedule schedule) {
        this.schedule = schedule;
    }

    public Long getId() {
        return this.schedule.getId();
    }

    public LocalDateTime getStartAt() {
        return schedule.getStartAt();
    }

    public LocalDateTime getEndAt() {
        return schedule.getEndAt();
    }

    public User getWriter() {
        return this.schedule.getWriter();
    }

    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return this.getStartAt().isBefore(endAt) && startAt.isBefore(this.getEndAt());
    }

    public String getTile() {
        return this.schedule.getTitle();
    }

    public Period getPeriod() {
        return Period.of(this.schedule.getStartAt(), this.schedule.getEndAt());
    }
}
