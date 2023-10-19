package com.smallgolemduo.togethersee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallgolemduo.togethersee.dto.request.BoardCreateRequest;
import com.smallgolemduo.togethersee.dto.response.BoardCreateResponse;
import com.smallgolemduo.togethersee.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.smallgolemduo.togethersee.entity.enums.Genre.ACTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService boardService;

    @Test
    @DisplayName("게시글 등록")
    void create() throws Exception {
        // given
        BoardCreateRequest boardCreateRequest = new BoardCreateRequest(
                "이경영이 말한다.", "영차!", "이경영", ACTION, 1L);
        BoardCreateResponse boardCreateResponse = new BoardCreateResponse(
                2L, "이경영이 말한다.", "영차!", "이경영", 365L, 1L, ACTION);
        given(boardService.create(any())).willReturn(boardCreateResponse);
        // when & then
        String valueAsString = objectMapper.writeValueAsString(boardCreateRequest);
        mockMvc.perform(post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("이경영이 말한다."))
                .andExpect(jsonPath("$.content").value("영차!"))
                .andExpect(jsonPath("$.author").value("이경영"))
                .andExpect(jsonPath("$.likes").value(365L))
                .andExpect(jsonPath("$.dislikes").value(1L))
                .andExpect(jsonPath("$.genre").value("ACTION"))
                .andDo(print());
    }

}