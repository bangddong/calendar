package com.study.calendar.api.dto;

import com.study.calendar.core.domain.RequestReplyType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ReplyEngagementReq {

    @NotNull
    private RequestReplyType type;

}
