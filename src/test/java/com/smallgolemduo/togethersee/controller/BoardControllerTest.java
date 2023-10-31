package com.smallgolemduo.togethersee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallgolemduo.togethersee.dto.BoardPayload;
import com.smallgolemduo.togethersee.dto.CommentPayload;
import com.smallgolemduo.togethersee.dto.request.*;
import com.smallgolemduo.togethersee.dto.response.*;
import com.smallgolemduo.togethersee.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.smallgolemduo.togethersee.entity.enums.MovieType.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @DisplayName("게시글 삭제")
    void deleted() throws Exception {
        // given
        Long boardId = new Random().nextLong();
        given(boardService.deleted(any())).willReturn(true);

        // when & then
        mockMvc.perform(delete("/api/boards/{boardId}", boardId))
                .andExpect(status().isOk())
                .andDo(print());
        verify(boardService).deleted(boardId);
    }

    @Test
    @DisplayName("댓글 등록")
    void createComment() throws Exception {
        // given
        Long boardId = new Random().nextLong();
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(
                "추천할게요", 1L);
        CreateCommentResponse createCommentResponse = new CreateCommentResponse(
                new CommentPayload(1L, "추천할게요", "최성욱", boardId, 1L));
        given(boardService.createComment(any(), any())).willReturn(createCommentResponse);

        // when & then
        String valueAsString = objectMapper.writeValueAsString(createCommentRequest);
        mockMvc.perform(post("/api/boards/{boardId}/comments", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentPayload.id").value(1L))
                .andExpect(jsonPath("$.commentPayload.content").value("추천할게요"))
                .andExpect(jsonPath("$.commentPayload.username").value("최성욱"))
                .andExpect(jsonPath("$.commentPayload.boardId").value(boardId))
                .andExpect(jsonPath("$.commentPayload.userId").value(1L))
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() throws Exception {
        // given
        Long boardId = new Random().nextLong();
        Long commentId = new Random().nextLong();
        UpdateCommentRequest updateCommentRequest =new UpdateCommentRequest("새롭게 재밌어", 1L);
        UpdateCommentResponse updateCommentResponse = new UpdateCommentResponse(
                new CommentPayload(commentId, "새롭게 재밌어", "최성욱", boardId, 1L));
        given(boardService.updateComment(any(), any(), any())).willReturn(updateCommentResponse);

        // when & then
        String valueAsString = objectMapper.writeValueAsString(updateCommentRequest);
        mockMvc.perform(put("/api/boards/{boardId}/comments/{commentId}", boardId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentPayload.id").value(commentId))
                .andExpect(jsonPath("$.commentPayload.content").value("새롭게 재밌어"))
                .andExpect(jsonPath("$.commentPayload.username").value("최성욱"))
                .andExpect(jsonPath("$.commentPayload.boardId").value(boardId))
                .andExpect(jsonPath("$.commentPayload.userId").value(1L))
                .andDo(print());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {
        // given
        DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest(2L);
        given(boardService.deleteComment(any(), any(), any())).willReturn(true);

        // when & then
        String valueAsString = objectMapper.writeValueAsString(deleteCommentRequest);
        mockMvc.perform(delete("/api/boards/{boardId}/comments/{commentId}", 1L, 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(valueAsString))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(true)))
                .andDo(print());
    }

}