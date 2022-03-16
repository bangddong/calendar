package com.study.calendar.core.util;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Period {

    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    public Period(LocalDateTime startAt, LocalDateTime endAt) {
        this.startAt = startAt;
        if (endAt == null) {
            this.endAt = startAt;
        } else {
            this.endAt = endAt;
        }
    }

    public static Period of(LocalDateTime startAt, LocalDateTime endAt) {
        return new Period(startAt, endAt);
    }

    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return this.startAt.isBefore(endAt) && startAt.isBefore(this.endAt);
    }

    public boolean isOverlapped(LocalDate date) {
        return isOverlapped(
                date.atStartOfDay(),
                LocalDateTime.of(date, LocalTime.of(23, 59, 59, 9999999)));
    }

}
