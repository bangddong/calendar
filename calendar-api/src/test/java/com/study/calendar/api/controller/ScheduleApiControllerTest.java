package com.study.calendar.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.calendar.api.dto.*;
import com.study.calendar.api.service.*;
import com.study.calendar.core.domain.RequestReplyType;
import com.study.calendar.core.domain.RequestStatus;
import com.study.calendar.core.domain.ScheduleType;
import com.study.calendar.core.exception.ErrorCode;
import com.study.calendar.core.util.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.study.calendar.api.service.LoginService.LOGIN_SESSION_KEY;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @DisplayName("[API][POST] 스케줄(task) 등록 - 성공")
    @Test
    void givenTaskCreateRequestAndAuthUser_whenCreateTask_then_SuccessResponse() throws Exception {
        // Given
        TaskCreateReq request = new TaskCreateReq(
                "여행가기",
                "동해",
                LocalDateTime.now()
        );

        session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY, AuthUser.of(1L));

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
                .andExpect(jsonPath("$.data").value(true))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][POST] 스케줄 등록(task) - 실패, 유저 정보 없음")
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
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()));
    }

    @DisplayName("[API][POST] 스케줄(event) 등록 - 성공")
    @Test
    void givenEventCreateRequestAndAuthUser_whenCreateEvent_thenSuccessResponse() throws Exception {
        // Given
        EventCreateReq request = new EventCreateReq(
                "여행가기",
                "동해",
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(2L)
        );
        session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY, AuthUser.of(1L));

        // When
        final ResultActions actions = mvc.perform(
                post("/api/schedules/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
                        .session(session)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][POST] 스케줄 등록(event) - 실패, 유저 정보 없음")
    @Test
    void givenEventCreateRequest_whenCreateEvent_thenFailResponse() throws Exception {
        // Given
        EventCreateReq request = new EventCreateReq(
                "여행가기",
                "동해",
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(2L)
        );

        // When
        final ResultActions actions = mvc.perform(
                post("/api/schedules/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // Then
        actions
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()));
    }

    @DisplayName("[API][POST] 스케줄 등록(notification) - 성공")
    @Test
    void givenNotificationCreateRequestAndAuthUser_whenCreateNotification_thenReturnSuccessResponse() throws Exception{
        // Given
        NotificationCreateReq request = new NotificationCreateReq(
                "새벽 운동",
                LocalDateTime.now(),
                new NotificationCreateReq.RepeatInfo(
                        new NotificationCreateReq.Interval(22, TimeUnit.DAY),
                        5
                )
        );

        session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY, AuthUser.of(1L));

        // When
        final ResultActions actions = mvc.perform(
                post("/api/schedules/notifications")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
                    .session(session)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    @DisplayName("[API][POST] 스케줄 등록(notification) - 실패, 유저 정보 없음")
    @Test
    void givenNotificationCreateRequest_whenCreateNotification_thenReturnFailResponse() throws Exception{
        // Given
        NotificationCreateReq request = new NotificationCreateReq(
                "새벽 운동",
                LocalDateTime.now(),
                new NotificationCreateReq.RepeatInfo(
                        new NotificationCreateReq.Interval(22, TimeUnit.DAY),
                        5
                )
        );

        // When
        final ResultActions actions = mvc.perform(
                post("/api/schedules/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // Then
        actions
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST.getMessage()));
    }

    @DisplayName("[API][GET] 일 별 스케줄 조회 - 성공")
    @Test
    void givenAuthUser_whenRequestScheduleByDay_thenReturnScheduleByDay() throws Exception{
        // Given
        AuthUser authUser = AuthUser.of(1L);
        LocalDate localDate = LocalDate.of(2022, 3, 15);

        given(scheduleQueryService.getScheduleByDay(authUser, localDate))
        .willReturn(List.of(TaskDto.builder()
                .scheduleId(1L)
                .taskAt(LocalDateTime.of(2022, 3, 15, 11, 14, 0))
                .description("벤치프레스")
                .writerId(1L)
                .title("운동가기")
                .build()
        ));

        session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY, authUser);

        // When
        final ResultActions actions = mvc.perform(
                get("/api/schedules/day")
                    .queryParam("date", "2022-03-15")
                    .session(session)
        );
        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].scheduleId").value(1L))
                .andExpect(jsonPath("$.data[0].taskAt").value(
                        LocalDateTime
                                .of(2022, 3, 15, 11, 14, 0)
                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                )
                .andExpect(jsonPath("$.data[0].title").value("운동가기"))
                .andExpect(jsonPath("$.data[0].description").value("벤치프레스"))
                .andExpect(jsonPath("$.data[0].writerId").value(1L))
                .andExpect(jsonPath("$.data[0].scheduleType").value(ScheduleType.TASK.name()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
        then(scheduleQueryService).should().getScheduleByDay(authUser, localDate);

    }

    @DisplayName("[API][GET] 주 별 스케줄 조회 - 성공")
    @Test
    void givenAuthUser_whenRequestScheduleByWeek_thenReturnScheduleByWeek() throws Exception{
        // Given
        AuthUser authUser = AuthUser.of(1L);
        LocalDate localDate = LocalDate.of(2022, 3, 15);

        given(scheduleQueryService.getScheduleByWeek(authUser, localDate))
                .willReturn(List.of(TaskDto.builder()
                        .scheduleId(1L)
                        .taskAt(LocalDateTime.of(2022, 3, 15, 11, 14, 0))
                        .description("벤치프레스")
                        .writerId(1L)
                        .title("운동가기")
                        .build()
                ));

        session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY, authUser);

        // When
        final ResultActions actions = mvc.perform(
                get("/api/schedules/week")
                        .queryParam("startOfWeek", "2022-03-15")
                        .session(session)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].scheduleId").value(1L))
                .andExpect(jsonPath("$.data[0].taskAt").value(
                        LocalDateTime
                                .of(2022, 3, 15, 11, 14, 0)
                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                )
                .andExpect(jsonPath("$.data[0].title").value("운동가기"))
                .andExpect(jsonPath("$.data[0].description").value("벤치프레스"))
                .andExpect(jsonPath("$.data[0].writerId").value(1L))
                .andExpect(jsonPath("$.data[0].scheduleType").value(ScheduleType.TASK.name()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        then(scheduleQueryService).should().getScheduleByWeek(authUser, localDate);

    }

    @DisplayName("[API][GET] 월 별 스케줄 조회 - 성공")
    @Test
    void givenAuthUser_whenRequestScheduleByMonth_thenReturnScheduleByMonth() throws Exception{
        // Given
        AuthUser authUser = AuthUser.of(1L);
        YearMonth localDate = YearMonth.of(2022, 3);

        given(scheduleQueryService.getScheduleByMonth(authUser, localDate))
                .willReturn(List.of(TaskDto.builder()
                        .scheduleId(1L)
                        .taskAt(LocalDateTime.of(2022, 3, 15, 11, 14, 0))
                        .description("벤치프레스")
                        .writerId(1L)
                        .title("운동가기")
                        .build()
                ));

        session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY, authUser);

        // When
        final ResultActions actions = mvc.perform(
                get("/api/schedules/month")
                        .queryParam("yearMonth", "2022-03")
                        .session(session)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].scheduleId").value(1L))
                .andExpect(jsonPath("$.data[0].taskAt").value(
                        LocalDateTime
                                .of(2022, 3, 15, 11, 14, 0)
                                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                )
                .andExpect(jsonPath("$.data[0].title").value("운동가기"))
                .andExpect(jsonPath("$.data[0].description").value("벤치프레스"))
                .andExpect(jsonPath("$.data[0].writerId").value(1L))
                .andExpect(jsonPath("$.data[0].scheduleType").value(ScheduleType.TASK.name()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
        then(scheduleQueryService).should().getScheduleByMonth(authUser, localDate);

    }

    @DisplayName("[API][PUT] 약속 변경 - 성공")
    @Test
    void givenReplyEngagementRequestAndAuthUser_whenRequestUpdateEngagement_thenReturnRequestStatus() throws Exception{
        // Given
        AuthUser authUser = AuthUser.of(1L);
        long engagementId = 1L;
        ReplyEngagementReq replyReq = new ReplyEngagementReq();
        replyReq.setType(RequestReplyType.ACCEPT);

        given(engagementService.update(authUser, engagementId, replyReq.getType()))
                .willReturn(RequestStatus.ACCEPTED);

        session = new MockHttpSession();
        session.setAttribute(LOGIN_SESSION_KEY, authUser);

        // When
        final ResultActions actions = mvc.perform(
                put("/api/schedules/events/engagements/" + engagementId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(replyReq))
                        .session(session)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").value(RequestStatus.ACCEPTED.name()))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
        then(engagementService).should().update(authUser, engagementId, RequestReplyType.ACCEPT);
    }

}