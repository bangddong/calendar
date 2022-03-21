package com.study.calendar.api.controller;

import com.study.calendar.core.domain.RequestReplyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("VIEW 컨트롤러 - 기본페이지")
@WebMvcTest(IndexController.class)
class IndexControllerTest {

    private final MockMvc mvc;
    private MockHttpSession session;

    public IndexControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[VIEW][GET] 인덱스 페이지")
    @Test
    void givenNothing_whenRequestRootPage_thenReturnIndexPage() throws Exception {
        // Given
        session = new MockHttpSession();
        session.setAttribute("USER_ID", 1);

        // When
        final ResultActions actions = mvc.perform(
                get("/")
                    .session(session)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("index"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("isSignIn", true))
                .andDo(print());
    }

    @DisplayName("[VIEW][GET] 스케줄 참석여부 업데이트 페이지")
    @Test
    void givenEngagementId_whenRequestUpdateEventPage_thenReturnUpdateEventPage() throws Exception {
        // Given
        long engagementId = 1L;
        session = new MockHttpSession();
        session.setAttribute("USER_ID", 1);
        RequestReplyType type = RequestReplyType.ACCEPT;

        // When
        final ResultActions actions = mvc.perform(
                get("/events/engagements/" + engagementId)
                        .queryParam("type", type.name())
                .session(session)
        );

        // Then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("update-event"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("isSignIn", true))
                .andExpect(model().attribute("updateType", RequestReplyType.ACCEPT.name()))
                .andExpect(model().attribute("engagementId", 1L))
                .andExpect(model().attribute("path", "/events/engagements/" + engagementId + "?type=" + type))
                .andDo(print());
    }

}