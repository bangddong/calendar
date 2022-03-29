package com.study.calendar.api.controller;

import com.study.calendar.api.service.EmailService;
import com.study.calendar.api.util.ApiUtils.ApiDataResponse;
import lombok.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static com.study.calendar.api.util.ApiUtils.success;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class BatchController {

    private final EmailService emailService;

    @PostMapping("/batch/send/mail")
    public ApiDataResponse<Boolean> sendMail(@RequestBody List<SendMailBatchReq> req) {
        req.forEach(emailService::sendAlarmMail);
        return success(true);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Getter
    @Setter
    public static class SendMailBatchReq {

        private Long id;
        private LocalDateTime startAt;
        private String title;
        private String userEmail;

    }

}
