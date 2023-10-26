package com.smallgolemduo.togethersee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallgolemduo.togethersee.dto.BoardPayload;
import com.smallgolemduo.togethersee.dto.request.CreateBoardRequest;
import com.smallgolemduo.togethersee.dto.response.CreateBoardResponse;
import com.smallgolemduo.togethersee.dto.request.UpdateBoardRequest;
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

import static com.smallgolemduo.togethersee.entity.enums.MovieType.ACTION;
import static com.smallgolemduo.togethersee.entity.enums.MovieType.DRAMA_DOCUMENTARY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

//    @Test
//    @DisplayName("게시글 전체 조회")
//    void findAll() throws Exception {
//        // given
//        List<BoardFindAllResponse> boardFindAllResponses = List.of(
//                new BoardFindAllResponse("안녕", "최성욱입니다.", "최성욱", 0L, 1L, Genre.ETC),
//                new BoardFindAllResponse("출첵", "출석체크할게요", "박상민", 10L, 1L, Genre.ETC));
//        given(boardService.findAll()).willReturn(boardFindAllResponses);
//
//        // when & then
//        mockMvc.perform(get("/api/boards"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(boardFindAllResponses.size()))
//                .andExpect(jsonPath("$[0].title").value(boardFindAllResponses.get(0).getTitle()))
//                .andExpect(jsonPath("$[0].content").value(boardFindAllResponses.get(0).getContent()))
//                .andExpect(jsonPath("$[0].author").value(boardFindAllResponses.get(0).getAuthor()))
//                .andExpect(jsonPath("$[0].likes").value(boardFindAllResponses.get(0).getLikes()))
//                .andExpect(jsonPath("$[0].dislikes").value(boardFindAllResponses.get(0).getDislikes()))
//                .andExpect(jsonPath("$[0].genre").value(boardFindAllResponses.get(0).getGenre().toString()))
//                .andExpect(jsonPath("$[1].title").value(boardFindAllResponses.get(1).getTitle()))
//                .andExpect(jsonPath("$[1].content").value(boardFindAllResponses.get(1).getContent()))
//                .andExpect(jsonPath("$[1].author").value(boardFindAllResponses.get(1).getAuthor()))
//                .andExpect(jsonPath("$[1].likes").value(boardFindAllResponses.get(1).getLikes()))
//                .andExpect(jsonPath("$[1].dislikes").value(boardFindAllResponses.get(1).getDislikes()))
//                .andExpect(jsonPath("$[1].genre").value(boardFindAllResponses.get(1).getGenre().toString()))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("게시글 전체 수정")
//    void update_all() throws Exception {
//        // given
//        Long boardId = 1L;
//        UpdateBoardRequest boardRequest = new UpdateBoardRequest("새로운 제목", "새로운 내용",
//                "새로운 작성자", Genre.ETC);
//        UpdateBoardResponse boardResponse = new UpdateBoardResponse(1L, "새로운 제목", "새로운 내용",
//                "새로운 작성자", 1L, 3L, Genre.ETC);
//        given(boardService.update(any(), any())).willReturn(boardResponse);
//
//        // when & then
//        String writeValueAsString = objectMapper.writeValueAsString(boardRequest);
//        mockMvc.perform(put("/api/boards/{boardId}", boardId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(writeValueAsString))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("새로운 제목"))
//                .andExpect(jsonPath("$.content").value("새로운 내용"))
//                .andExpect(jsonPath("$.author").value("새로운 작성자"))
//                .andExpect(jsonPath("$.likes").value(1L))
//                .andExpect(jsonPath("$.dislikes").value(3L))
//                .andExpect(jsonPath("$.genre").value("ETC"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("게시글 제목 수정")
//    void update_title() throws Exception {
//        // given
//        Long boardId = 2L;
//        UpdateBoardRequest boardRequest = new UpdateBoardRequest("제목", "안녕",
//                "최성욱", Genre.ACTION);
//        UpdateBoardResponse boardResponse = new UpdateBoardResponse(boardId, "새로운 제목", "안녕",
//                "최성욱", 1L, 2L, Genre.ACTION);
//        given(boardService.update(any(), any())).willReturn(boardResponse);
//
//        //when & than
//        String writeValueAsString = objectMapper.writeValueAsString(boardRequest);
//        mockMvc.perform(put("/api/boards/{boardId}", boardId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(writeValueAsString))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(2L))
//                .andExpect(jsonPath("$.title").value("새로운 제목"))
//                .andExpect(jsonPath("$.content").value("안녕"))
//                .andExpect(jsonPath("$.author").value("최성욱"))
//                .andExpect(jsonPath("$.likes").value(1L))
//                .andExpect(jsonPath("$.dislikes").value(2L))
//                .andExpect(jsonPath("$.genre").value("ACTION"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("게시글 내용 수정")
//    void update_content() throws Exception {
//        // given
//        Long boardId = 3L;
//        UpdateBoardRequest boardRequest = new UpdateBoardRequest("제목", "안녕",
//                "최성욱", Genre.ACTION);
//        UpdateBoardResponse boardResponse = new UpdateBoardResponse(boardId, "제목", "새로운 내용",
//                "최성욱", 1L, 2L, Genre.ACTION);
//        given(boardService.update(any(), any())).willReturn(boardResponse);
//
//        //when & than
//        String writeValueAsString = objectMapper.writeValueAsString(boardRequest);
//        mockMvc.perform(put("/api/boards/{boardId}", boardId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(writeValueAsString))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(3L))
//                .andExpect(jsonPath("$.title").value("제목"))
//                .andExpect(jsonPath("$.content").value("새로운 내용"))
//                .andExpect(jsonPath("$.author").value("최성욱"))
//                .andExpect(jsonPath("$.likes").value(1L))
//                .andExpect(jsonPath("$.dislikes").value(2L))
//                .andExpect(jsonPath("$.genre").value("ACTION"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("게시글 작성자 수정")
//    void update_author() throws Exception {
//        // given
//        Long boardId = 4L;
//        UpdateBoardRequest boardRequest = new UpdateBoardRequest("제목", "내용",
//                "최성욱", Genre.ACTION);
//        UpdateBoardResponse boardResponse = new UpdateBoardResponse(boardId, "제목", "내용",
//                "새로운 작성자", 1L, 2L, Genre.ACTION);
//        given(boardService.update(any(), any())).willReturn(boardResponse);
//
//        //when & than
//        String writeValueAsString = objectMapper.writeValueAsString(boardRequest);
//        mockMvc.perform(put("/api/boards/{boardId}", boardId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(writeValueAsString))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(4L))
//                .andExpect(jsonPath("$.title").value("제목"))
//                .andExpect(jsonPath("$.content").value("내용"))
//                .andExpect(jsonPath("$.author").value("새로운 작성자"))
//                .andExpect(jsonPath("$.likes").value(1L))
//                .andExpect(jsonPath("$.dislikes").value(2L))
//                .andExpect(jsonPath("$.genre").value("ACTION"))
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("게시글 장르 수정")
//    void update_genre() throws Exception {
//        // given
//        Long boardId = 5L;
//        UpdateBoardRequest boardRequest = new UpdateBoardRequest("제목", "안녕",
//                "최성욱", Genre.ACTION);
//        UpdateBoardResponse boardResponse = new UpdateBoardResponse(boardId, "제목", "새로운 내용",
//                "최성욱", 1L, 2L, Genre.SF_FANTASY);
//        given(boardService.update(any(), any())).willReturn(boardResponse);
//
//        //when & than
//        String writeValueAsString = objectMapper.writeValueAsString(boardRequest);
//        mockMvc.perform(put("/api/boards/{boardId}", boardId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(writeValueAsString))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(5L))
//                .andExpect(jsonPath("$.title").value("제목"))
//                .andExpect(jsonPath("$.content").value("새로운 내용"))
//                .andExpect(jsonPath("$.author").value("최성욱"))
//                .andExpect(jsonPath("$.likes").value(1L))
//                .andExpect(jsonPath("$.dislikes").value(2L))
//                .andExpect(jsonPath("$.genre").value("SF_FANTASY"))
//                .andDo(print());
//    }
//
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