package com.study.calendar.core.domain.entity;

import com.study.calendar.core.domain.Event;
import com.study.calendar.core.domain.RequestReplyType;
import com.study.calendar.core.domain.RequestStatus;
import com.study.calendar.core.domain.ScheduleType;
import com.study.calendar.core.util.Period;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private RequestStatus requestStatus;

    public Engagement(Schedule eventSchedule, User attendee) {
        assert eventSchedule.getScheduleType() == ScheduleType.EVENT;
        this.schedule = eventSchedule;
        this.requestStatus = RequestStatus.REQUESTED;
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
        return requestStatus;
    }

    public boolean isOverlapped(Period period) {
        return schedule.isOverlapped(period);
    }

    public Engagement reply(RequestReplyType type) {
        switch (type) {
            case ACCEPT:
                this.requestStatus = RequestStatus.ACCEPTED;
                break;
            case REJECT:
                this.requestStatus = RequestStatus.REJECTED;
                break;
        }
        return this;
    }
}
