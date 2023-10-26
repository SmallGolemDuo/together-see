package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import com.smallgolemduo.togethersee.dto.UserPayload;
import com.smallgolemduo.togethersee.dto.request.CreateBoardRequest;
import com.smallgolemduo.togethersee.dto.response.CreateBoardResponse;
import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.User;
import com.smallgolemduo.togethersee.dto.request.UpdateBoardRequest;
import com.smallgolemduo.togethersee.dto.response.UpdateBoardResponse;
import com.smallgolemduo.togethersee.dto.response.FindByIdBoardResponse;
import com.smallgolemduo.togethersee.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.smallgolemduo.togethersee.entity.enums.MovieType.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BoardService boardService;

    @Test
    @DisplayName("게시물 등록")
    void create() {
        // given
        UserPayload userPayload = UserPayload.builder()
                .id(1L).username("성욱").email("asd@naver.com")
                .password("1234").birth("950928").phoneNumber("010-1234-1234").board(List.of())
                .build();
        given(userService.findById(any())).willReturn(userPayload);

        Board board = Board.builder()
                .id(1L).title("이거 재밌네").content("극한직업").likes(1L)
                .dislikes(1L).movieType(ROMANCE_COMEDY).user(userPayload.toEntity())
                .build();
        given(boardRepository.save(any())).willReturn(board);

        CreateBoardResponse expectedValue = CreateBoardResponse.from(BoardPayload.from(
                Board.builder()
                        .id(1L).title("이거 재밌네").content("극한직업").likes(1L)
                        .dislikes(1L).movieType(ROMANCE_COMEDY)
                        .user(User.builder()
                                .id(1L).username("성욱").email("asd@naver.com")
                                .password("1234").birth("950928").phoneNumber("010-1234-1234").boards(List.of())
                                .build())
                        .build()));

        // when
        CreateBoardResponse resultValue = boardService.create(CreateBoardRequest.builder()
                .title("이거 재밌네")
                .content("극한직업")
                .movieType(ROMANCE_COMEDY)
                .userId(1L)
                .build());

        // then
        assertThat(expectedValue).usingRecursiveComparison().isEqualTo(resultValue);
    }

    @DisplayName("게시글 수정")
    void update() {
        // given
        Long boardId = 1L;
        Board board = Board.builder()
                .id(boardId)
                .title("제목입니다")
                .content("내용입니다")
                .author("최성욱")
                .likes(1L)
                .dislikes(2L)
                .genre(Genre.DRAMA_DOCUMENTARY)
                .userId(3L)
                .build();
        given(boardRepository.findById(any())).willReturn(Optional.ofNullable(board));
        Board modifyBoard = Board.builder()
                .id(boardId)
                .title("새로운 제목")
                .content("새로운 내용")
                .author("새로운 작성자")
                .likes(1L)
                .dislikes(2L)
                .genre(Genre.DRAMA_DOCUMENTARY)
                .userId(3L)
                .build();
        given(boardRepository.save(any())).willReturn(modifyBoard);
        UpdateBoardRequest boardRequest = new UpdateBoardRequest(
                "새로운 제목", "새로운 내용", "새로운 작성자", Genre.DRAMA_DOCUMENTARY);
        UpdateBoardResponse boardResponse = new UpdateBoardResponse(
                boardId, "새로운 제목", "새로운 내용", "새로운 작성자", 1L, 2L,
                Genre.DRAMA_DOCUMENTARY);

        // when
        UpdateBoardResponse expectedUpdateBoardResponse = boardService.update(boardId, boardRequest);

        // then
        assertThat(boardResponse).usingRecursiveComparison().isEqualTo(expectedUpdateBoardResponse);
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleted() {
        // given
        Long boardId = 4L;
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(Board.builder().build()));
        doNothing().when(boardRepository).delete(any());

        // when
        boolean isDelete = boardService.deleted(boardId);

        // then
        assertThat(isDelete).isTrue();
    }

    @Test
    @DisplayName("게시글 조회")
    void findById() {
        // given
        Long boardId = 2L;
        Board board = Board.builder()
                .id(boardId)
                .title("제목")
                .content("내용")
                .author("최성욱")
                .likes(1L)
                .dislikes(1L)
                .genre(Genre.ROMANCE_COMEDY)
                .userId(1L)
                .build();
        given(boardRepository.findById(any())).willReturn(Optional.ofNullable(board));
        FindByIdBoardResponse boardResponse = boardService.findById(boardId);

        // when
        FindByIdBoardResponse findByIdBoardResponse = new FindByIdBoardResponse(
                boardId, "제목", "내용", "최성욱", 1L, 1L, Genre.ROMANCE_COMEDY);

        // then
        assertThat(boardResponse).usingRecursiveComparison().isEqualTo(findByIdBoardResponse);
    }

}