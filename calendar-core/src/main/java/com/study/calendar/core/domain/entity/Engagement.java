package com.study.calendar.core.domain.entity;

import com.study.calendar.core.domain.Event;
import com.study.calendar.core.domain.RequestStatus;
import com.study.calendar.core.domain.ScheduleType;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Table(name = "engagements")
@Entity
public class Engagement extends BaseEntity {

    @JoinColumn(name = "schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;

    public Engagement(Schedule eventSchedule, User attendee) {
        assert eventSchedule.getScheduleType() == ScheduleType.EVENT;
        this.schedule = eventSchedule;
        this.status = RequestStatus.REQUESTED;
        this.attendee = attendee;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Event getEvent() {
        return schedule.toEvent();
    }

    public User getAttendee() {
        return attendee;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public boolean isOverlapped(LocalDate date) {
        return this.schedule.isOverlapped(date);
    }
}
