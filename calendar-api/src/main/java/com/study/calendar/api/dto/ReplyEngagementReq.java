package com.study.calendar.api.dto;

import com.study.calendar.core.domain.RequestReplyType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReplyEngagementReq {

    private RequestReplyType type;

}
