package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import com.smallgolemduo.togethersee.dto.UserPayload;
import com.smallgolemduo.togethersee.dto.request.CreateBoardRequest;
import com.smallgolemduo.togethersee.dto.response.CreateBoardResponse;
import com.smallgolemduo.togethersee.dto.response.FindAllBoardResponse;
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
import java.util.Random;

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

    @Test
    @DisplayName("게시글 조회")
    void findById() {
        // given
        Long boardId = new Random().nextLong();
        Board board = Board.builder()
                .id(boardId).title("이거 재밌네").content("극한직업").likes(1L)
                .dislikes(1L).movieType(ROMANCE_COMEDY).user(User.builder()
                        .id(1L).username("성욱").email("asd@naver.com")
                        .password("1234").birth("950928").phoneNumber("010-1234-1234").boards(List.of())
                        .build())
                .build();
        given(boardRepository.findById(any())).willReturn(Optional.ofNullable(board));

        FindByIdBoardResponse expectedValue = FindByIdBoardResponse.from(BoardPayload.from(
                Board.builder()
                        .id(boardId).title("이거 재밌네").content("극한직업").likes(1L)
                        .dislikes(1L).movieType(ROMANCE_COMEDY)
                        .user(User.builder()
                                .id(1L).username("성욱").email("asd@naver.com")
                                .password("1234").birth("950928").phoneNumber("010-1234-1234").boards(List.of())
                                .build())
                        .build()));

        // when
        FindByIdBoardResponse findByIdBoardResponse = boardService.findById(boardId);

        // then
        assertThat(findByIdBoardResponse).usingRecursiveComparison().isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void findAll() {
        // given
        User user = User.builder()
                .id(1L).username("성욱").email("asd@naver.com")
                .password("1234").birth("950928").phoneNumber("010-1234-1234").boards(List.of())
                .build();
        List<Board> boards = List.of(
                new Board(1L, "제목1", "내용1", 1L, 2L, ROMANCE_COMEDY, user),
                new Board(2L, "제목2", "내용2", 2L, 3L, ROMANCE_COMEDY, user));
        List<FindAllBoardResponse> expectedValue = List.of(
                new FindAllBoardResponse(List.of(
                        new BoardPayload(1L, "제목1", "내용1", 1L, 2L, ROMANCE_COMEDY, 1L))),
                new FindAllBoardResponse(List.of(
                        new BoardPayload(2L, "제목2", "내용2", 2L, 3L, ROMANCE_COMEDY, 1L))));
        given(boardRepository.findAll()).willReturn(boards);

        // when
        List<FindAllBoardResponse> findAllBoardResponses = boardService.findAll();

        // then
        assertThat(findAllBoardResponses).usingRecursiveComparison().isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("게시글 수정")
    void update() {
        // given
        Long boardId = new Random().nextLong();
        User user = User.builder()
                .id(1L).username("성욱").email("asd@naver.com")
                .password("1234").birth("950928").phoneNumber("010-1234-1234").boards(List.of())
                .build();
        Board board = Board.builder()
                .id(boardId).title("제목입니다").content("내용입니다")
                .likes(1L).dislikes(2L).movieType(DRAMA_DOCUMENTARY).user(user)
                .build();
        given(boardRepository.findById(any())).willReturn(Optional.ofNullable(board));

        Board updateBoard = Board.builder()
                .id(boardId).title("새로운 제목").content("새로운 내용")
                .likes(1L).dislikes(2L).movieType(ACTION).user(user)
                .build();
        given(boardRepository.save(any())).willReturn(updateBoard);
        UpdateBoardRequest updateBoardRequest = new UpdateBoardRequest("새로운 제목", "새로운 내용", ACTION);
        UpdateBoardResponse expectedBoardResponse = new UpdateBoardResponse(
                new BoardPayload(boardId, "새로운 제목", "새로운 내용", 1L, 2L,
                        ACTION, 1L));

        // when
        UpdateBoardResponse updateBoardResponse = boardService.update(boardId, updateBoardRequest);

        // then
        assertThat(updateBoardResponse).usingRecursiveComparison().isEqualTo(expectedBoardResponse);
    }
//
//    @Test
//    @DisplayName("게시글 삭제")
//    void deleted() {
//        // given
//        Long boardId = 4L;
//        given(boardRepository.findById(anyLong())).willReturn(Optional.of(Board.builder().build()));
//        doNothing().when(boardRepository).delete(any());
//
//        // when
//        boolean isDelete = boardService.deleted(boardId);
//
//        // then
//        assertThat(isDelete).isTrue();
//    }
//

}