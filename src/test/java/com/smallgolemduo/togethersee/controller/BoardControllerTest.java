package com.smallgolemduo.togethersee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallgolemduo.togethersee.dto.BoardPayload;
import com.smallgolemduo.togethersee.dto.request.CreateBoardRequest;
import com.smallgolemduo.togethersee.dto.response.CreateBoardResponse;
import com.smallgolemduo.togethersee.dto.request.UpdateBoardRequest;
import com.smallgolemduo.togethersee.dto.response.FindAllBoardResponse;
import com.smallgolemduo.togethersee.dto.response.UpdateBoardResponse;
import com.smallgolemduo.togethersee.dto.response.FindByIdBoardResponse;
import com.smallgolemduo.togethersee.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.smallgolemduo.togethersee.entity.enums.MovieType.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(BoardController.class)
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
        CreateBoardRequest createBoardRequest = new CreateBoardRequest(
                "안녕하세요", "최성욱입니다", ACTION, 1L);
        BoardPayload boardPayload = new BoardPayload(
                1L, "안녕하세요", "최성욱입니다", 0L, 1L, ACTION, 1L);
        CreateBoardResponse createBoardResponse = new CreateBoardResponse(boardPayload);
        given(boardService.create(any())).willReturn(createBoardResponse);

        // when & then
        String valueAsString = objectMapper.writeValueAsString(createBoardRequest);
        mockMvc.perform(post("/api/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardPayload.id").value(1L))
                .andExpect(jsonPath("$.boardPayload.title").value("안녕하세요"))
                .andExpect(jsonPath("$.boardPayload.content").value("최성욱입니다"))
                .andExpect(jsonPath("$.boardPayload.likes").value(0L))
                .andExpect(jsonPath("$.boardPayload.dislikes").value(1L))
                .andExpect(jsonPath("$.boardPayload.movieType").value("ACTION"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 조회")
    void findById() throws Exception {
        // given
        Long boardId = 1L;
        FindByIdBoardResponse findByIdBoardResponse = new FindByIdBoardResponse(BoardPayload.builder()
                .id(boardId).title("추천").content("엘리멘탈")
                .likes(1L).dislikes(0L).movieType(DRAMA_DOCUMENTARY).userId(1L)
                .build());
        given(boardService.findById(any())).willReturn(findByIdBoardResponse);

        // when & then
        mockMvc.perform(get("/api/boards/{id}", boardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardPayload.id").value(1L))
                .andExpect(jsonPath("$.boardPayload.title").value("추천"))
                .andExpect(jsonPath("$.boardPayload.content").value("엘리멘탈"))
                .andExpect(jsonPath("$.boardPayload.likes").value(1L))
                .andExpect(jsonPath("$.boardPayload.dislikes").value(0L))
                .andExpect(jsonPath("$.boardPayload.movieType").value("DRAMA_DOCUMENTARY"))
                .andExpect(jsonPath("$.boardPayload.userId").value(1L))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void findAll() throws Exception {
        // given
        List<FindAllBoardResponse> findAllBoardResponses = List.of(
                new FindAllBoardResponse(List.of(
                        new BoardPayload(1L, "출첵", "재밌다", 1L, 0L, ETC, 1L))),
                new FindAllBoardResponse(List.of(
                        new BoardPayload(2L, "출첵2", "재밌더라", 10L, 1L, ETC, 1L))));
        given(boardService.findAll()).willReturn(findAllBoardResponses);

        // when & then
        mockMvc.perform(get("/api/boards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(findAllBoardResponses.size()))
                .andExpect(jsonPath("$[0].boardPayloads[0].id").value(1L))
                .andExpect(jsonPath("$[0].boardPayloads[0].title").value("출첵"))
                .andExpect(jsonPath("$[0].boardPayloads[0].content").value("재밌다"))
                .andExpect(jsonPath("$[0].boardPayloads[0].likes").value(1L))
                .andExpect(jsonPath("$[0].boardPayloads[0].dislikes").value(0L))
                .andExpect(jsonPath("$[0].boardPayloads[0].movieType").value("ETC"))
                .andExpect(jsonPath("$[0].boardPayloads[0].userId").value(1L))
                .andExpect(jsonPath("$[1].boardPayloads[0].id").value(2L))
                .andExpect(jsonPath("$[1].boardPayloads[0].title").value("출첵2"))
                .andExpect(jsonPath("$[1].boardPayloads[0].content").value("재밌더라"))
                .andExpect(jsonPath("$[1].boardPayloads[0].likes").value(10L))
                .andExpect(jsonPath("$[1].boardPayloads[0].dislikes").value(1L))
                .andExpect(jsonPath("$[1].boardPayloads[0].movieType").value("ETC"))
                .andExpect(jsonPath("$[1].boardPayloads[0].userId").value(1L))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정")
    void update() throws Exception {
        // given
        Long boardId = 1L;
        UpdateBoardRequest boardRequest = new UpdateBoardRequest("새로운 제목", "새로운 내용", ETC);
        UpdateBoardResponse boardResponse = new UpdateBoardResponse(
                new BoardPayload(
                        1L, "새로운 제목", "새로운 내용", 1L, 3L, ETC, 1L));
        given(boardService.update(any(), any())).willReturn(boardResponse);

        // when & then
        String writeValueAsString = objectMapper.writeValueAsString(boardRequest);
        mockMvc.perform(put("/api/boards/{boardId}", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeValueAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.boardPayload.id").value(1L))
                .andExpect(jsonPath("$.boardPayload.title").value("새로운 제목"))
                .andExpect(jsonPath("$.boardPayload.content").value("새로운 내용"))
                .andExpect(jsonPath("$.boardPayload.likes").value(1L))
                .andExpect(jsonPath("$.boardPayload.dislikes").value(3L))
                .andExpect(jsonPath("$.boardPayload.movieType").value("ETC"))
                .andDo(print());
    }

//    @Test
//    @DisplayName("게시글 삭제")
//    void deleted() throws Exception {
//        // given
//        Long boardId = 3L;
//
//        // when & then
//        mockMvc.perform(delete("/api/boards/{boardId}", boardId))
//                .andExpect(status().isOk())
//                .andDo(print());
//        verify(boardService).deleted(boardId);
//    }

}