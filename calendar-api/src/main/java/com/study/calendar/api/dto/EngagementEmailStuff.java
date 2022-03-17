package com.study.calendar.api.dto;

import com.study.calendar.core.util.Period;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@RequiredArgsConstructor
public class EngagementEmailStuff {

    private static final String engagementUpdateApi = "http://localhost:8080/events/engagements/";
    private final Long engagementId;
    private final String toEmail;
    private final List<String> attendeeEmails;
    private final String title;
    private final Period period;

    public String getSubject() {
        return new StringBuilder()
                .append("[초대장] ")
                .append(title)
                .append(" - ")
                .append(period.toString())
                .append("(")
                .append(toEmail)
                .append(")")
                .toString();
    }

    public String getToEmail() {
        return this.toEmail;
    }

    public Map<String, Object> getProps() {
        final Map<String, Object> props = new HashMap<>();
        props.put("title", title);
        props.put("calendar", toEmail);
        props.put("period", period.toString());
        props.put("attendees", attendeeEmails);
        props.put("acceptUrl", engagementUpdateApi + engagementId + "?type=ACCEPT");
        props.put("rejectUrl", engagementUpdateApi + engagementId + "?type=REJECT");

        return props;
    }

}
