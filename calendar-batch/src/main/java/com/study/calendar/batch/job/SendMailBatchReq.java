package com.study.calendar.batch.job;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SendMailBatchReq {

    private Long id;
    private LocalDateTime startAt;
    private String title;
    private String userEmail;

}
