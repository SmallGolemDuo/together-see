package com.smallgolemduo.togethersee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallgolemduo.togethersee.dto.request.BoardCreateRequest;
import com.smallgolemduo.togethersee.dto.response.BoardCreateResponse;
import com.smallgolemduo.togethersee.dto.response.BoardFindAllResponse;
import com.smallgolemduo.togethersee.dto.response.BoardFindByIdResponse;
import com.smallgolemduo.togethersee.entity.enums.Genre;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    @DisplayName("게시글 조회")
    void findById() throws Exception {
        // given
        Long boardId = 1L;
        BoardFindByIdResponse boardFindByIdResponse = new BoardFindByIdResponse(
                "안녕하세요", "최성욱입니다.", "최성욱", 2L, 0L, Genre.SF_FANTASY);
        given(boardService.findById(boardId)).willReturn(boardFindByIdResponse);

        // when & then
        mockMvc.perform(get("/api/boards/{id}", boardId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("안녕하세요"))
                .andExpect(jsonPath("$.content").value("최성욱입니다."))
                .andExpect(jsonPath("$.author").value("최성욱"))
                .andExpect(jsonPath("$.likes").value(2L))
                .andExpect(jsonPath("$.dislikes").value(0L))
                .andExpect(jsonPath("$.genre").value("SF_FANTASY"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void findAll() throws Exception {
        // given
        List<BoardFindAllResponse> boardFindAllResponses = List.of(
                new BoardFindAllResponse("안녕", "최성욱입니다.", "최성욱", 0L, 1L, Genre.ETC),
                new BoardFindAllResponse("출첵", "출석체크할게요", "박상민", 10L, 1L, Genre.ETC));
        given(boardService.findAll()).willReturn(boardFindAllResponses);
        // when & then
        mockMvc.perform(get("/api/boards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(boardFindAllResponses.size()))
                .andExpect(jsonPath("$[0].title").value(boardFindAllResponses.get(0).getTitle()))
                .andExpect(jsonPath("$[0].content").value(boardFindAllResponses.get(0).getContent()))
                .andExpect(jsonPath("$[0].author").value(boardFindAllResponses.get(0).getAuthor()))
                .andExpect(jsonPath("$[0].likes").value(boardFindAllResponses.get(0).getLikes()))
                .andExpect(jsonPath("$[0].dislikes").value(boardFindAllResponses.get(0).getDislikes()))
                .andExpect(jsonPath("$[0].genre").value(boardFindAllResponses.get(0).getGenre().toString()))
                .andExpect(jsonPath("$[1].title").value(boardFindAllResponses.get(1).getTitle()))
                .andExpect(jsonPath("$[1].content").value(boardFindAllResponses.get(1).getContent()))
                .andExpect(jsonPath("$[1].author").value(boardFindAllResponses.get(1).getAuthor()))
                .andExpect(jsonPath("$[1].likes").value(boardFindAllResponses.get(1).getLikes()))
                .andExpect(jsonPath("$[1].dislikes").value(boardFindAllResponses.get(1).getDislikes()))
                .andExpect(jsonPath("$[1].genre").value(boardFindAllResponses.get(1).getGenre().toString()))
                .andDo(print());
    }

}