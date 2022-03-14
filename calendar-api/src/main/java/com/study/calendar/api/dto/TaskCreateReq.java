package com.study.calendar.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskCreateReq {

    private final String title;
    private final String description;
    private final LocalDateTime taskAt;
}
