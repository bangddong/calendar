package com.study.calendar.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.calendar.api.dto.SignUpReq;
import com.study.calendar.api.dto.TaskCreateReq;
import com.study.calendar.api.service.*;
import com.study.calendar.core.domain.Event;
import com.study.calendar.core.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static com.study.calendar.api.service.LoginService.LOGIN_SESSION_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("API 컨트롤러 - 스케줄")
@WebMvcTest(ScheduleApiController.class)
class ScheduleApiControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;
    private MockHttpSession session;

    @MockBean
    private TaskService taskService;
    @MockBean
    private ScheduleQueryService scheduleQueryService;
    @MockBean
    private EventService eventService;
    @MockBean
    private EngagementService engagementService;
    @MockBean
    private NotificationService notificationService;

    public ScheduleApiControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[API][POST] 스케줄 등록 - 성공")
    @Test
    void givenTaskCreateRequestAndAuthUser_whenCreateTask_then_SuccessResponse() throws Exception {
        // Given
        TaskCreateReq request = new TaskCreateReq(
                "여행가기",
                "동해",
                LocalDateTime.now()
        );
        session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY, 1L);

        // When
        final ResultActions actions = mvc.perform(
                post("/api/schedules/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .session(session)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[API][POST] 스케줄 등록 - 실패, 유저 정보 없음")
    @Test
    void givenTaskCreateRequest_whenCreateTask_then_FailResponse() throws Exception {
        // Given
        TaskCreateReq request = new TaskCreateReq(
                "여행가기",
                "동해",
                LocalDateTime.now()
        );

        // When
        final ResultActions actions = mvc.perform(
                post("/api/schedules/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // Then
        actions
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.BAD_REQUEST))
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()))
                .andDo(print());
    }

}