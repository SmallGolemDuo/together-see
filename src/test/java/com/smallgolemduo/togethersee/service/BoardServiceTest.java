package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import com.smallgolemduo.togethersee.dto.CommentPayload;
import com.smallgolemduo.togethersee.dto.UserPayload;
import com.smallgolemduo.togethersee.dto.request.*;
import com.smallgolemduo.togethersee.dto.response.*;
import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.Comment;
import com.smallgolemduo.togethersee.entity.User;
import com.smallgolemduo.togethersee.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.smallgolemduo.togethersee.entity.enums.MovieType.*;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
                .id(1L).username("성욱")
                .email("asd@naver.com").password("1234")
                .birth("950928").phoneNumber("010-1234-1234")
                .board(List.of())
                .build();
        given(userService.findById(any())).willReturn(userPayload);

        Board tempBoard = Board.builder()
                .id(1L).title("이거 재밌네")
                .content("극한직업").likes(1L)
                .dislikes(1L).movieType(ROMANCE_COMEDY)
                .user(userPayload.toEntity())
                .comments(new ArrayList<>())
                .build();
        tempBoard.addComments(Comment.builder()
                .content("추천해요")
                .userId(1L)
                .build());
        given(boardRepository.save(any())).willReturn(tempBoard);

        Board board = Board.builder()
                .id(1L)
                .title("이거 재밌네")
                .content("극한직업")
                .likes(1L)
                .dislikes(1L)
                .movieType(ROMANCE_COMEDY)
                .user(User.builder()
                        .id(1L).username("성욱")
                        .email("asd@naver.com").password("1234")
                        .birth("950928").phoneNumber("010-1234-1234")
                        .boards(List.of())
                        .build())
                .comments(new ArrayList<>())
                .build();
        board.addComments(Comment.builder()
                .content("추천해요")
                .userId(1L)
                .build());
        CreateBoardResponse expectedValue = CreateBoardResponse.from(BoardPayload.from(board));

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
    @DisplayName("게시글 상세 조회")
    void findById() {
        // given
        Long boardId = new Random().nextLong();
        Board board = Board.builder()
                .id(boardId)
                .title("이거 재밌네")
                .content("극한직업")
                .likes(1L)
                .dislikes(1L)
                .movieType(ROMANCE_COMEDY)
                .user(User.builder()
                        .id(1L).username("성욱")
                        .email("asd@naver.com").password("1234")
                        .birth("950928").phoneNumber("010-1234-1234")
                        .boards(new ArrayList<>())
                        .build())
                .comments(new ArrayList<>())
                .build();
        board.addComments(Comment.builder().content("짱 재밌네").userId(1L).build());
        given(boardRepository.findById(any())).willReturn(Optional.of(board));

        FindByIdBoardResponse expectedValue = FindByIdBoardResponse.from(
                new BoardPayload(boardId, "이거 재밌네", "극한직업", 1L, 1L, ROMANCE_COMEDY, 1L,
                        board.getComments().stream()
                                .map(CommentPayload::from)
                                .collect(Collectors.toList())));

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
                new Board(1L, "제목1", "내용1", 1L, 2L, ROMANCE_COMEDY, user, List.of()),
                new Board(2L, "제목2", "내용2", 2L, 3L, ROMANCE_COMEDY, user, List.of()));
        List<FindAllBoardResponse> expectedValue = List.of(
                new FindAllBoardResponse(List.of(
                        new BoardPayload(
                                1L, "제목1", "내용1", 1L, 2L, ROMANCE_COMEDY, 1L, List.of()))),
                new FindAllBoardResponse(List.of(
                        new BoardPayload(
                                2L, "제목2", "내용2", 2L, 3L, ROMANCE_COMEDY, 1L, List.of()))));
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
        given(boardRepository.findById(any())).willReturn(ofNullable(board));

        Board updateBoard = Board.builder()
                .id(boardId).title("새로운 제목").content("새로운 내용")
                .likes(1L).dislikes(2L).movieType(ACTION).user(user)
                .build();
        given(boardRepository.save(any())).willReturn(updateBoard);
        UpdateBoardRequest updateBoardRequest = new UpdateBoardRequest("새로운 제목", "새로운 내용", ACTION);
        UpdateBoardResponse expectedBoardResponse = new UpdateBoardResponse(
                new BoardPayload(boardId, "새로운 제목", "새로운 내용", 1L, 2L,
                        ACTION, 1L, List.of(new CommentPayload(
                        1L, "새로운 제목", "새로운 내용", boardId, 1L
                ))));

        // when
        UpdateBoardResponse updateBoardResponse = boardService.update(boardId, updateBoardRequest);

        // then
        assertThat(updateBoardResponse).usingRecursiveComparison().isEqualTo(expectedBoardResponse);
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleted() {
        // given
        Long boardId = new Random().nextLong();
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(Board.builder().build()));
        doNothing().when(boardRepository).delete(any());

        // when
        boolean isDelete = boardService.deleted(boardId);

        // then
        assertThat(isDelete).isTrue();
    }

    @Test
    @DisplayName("댓글 등록")
    void createComment() {
        // given
        Long boardId = new Random().nextLong();
        User user = User.builder()
                .id(1L).username("최성욱").email("asd@naver.com")
                .password("1234").birth("950928").phoneNumber("010-1234-1234").boards(List.of())
                .build();
        Board tempBoard = Board.builder()
                .id(boardId).title("제목입니다").content("내용입니다")
                .likes(1L).dislikes(2L).movieType(DRAMA_DOCUMENTARY).user(user)
                .comments(new ArrayList<>())
                .build();
        given(boardRepository.findById(any())).willReturn(ofNullable(tempBoard));

        UserPayload userPayload = UserPayload.builder()
                .id(1L).username("최성욱").email("asd@naver.com")
                .password("1234").birth("950928").phoneNumber("010-1234-1234").board(List.of())
                .build();
        given(userService.findById(any())).willReturn(userPayload);

        Board board = Board.builder()
                .id(boardId).title("제목입니다").content("내용입니다")
                .likes(1L).dislikes(2L).movieType(DRAMA_DOCUMENTARY).user(user)
                .comments(List.of(Comment.builder()
                        .id(1L).content("추천할게요").username("최성욱").board(tempBoard).userId(1L)
                        .build()))
                .build();
        given(boardRepository.save(any())).willReturn(board);

        CreateCommentResponse expectedValue = CreateCommentResponse.from(CommentPayload.from(
                Comment.builder()
                        .id(1L).content("추천할게요").username("최성욱").board(board).userId(1L)
                        .build()));

        // when
        CreateCommentResponse createCommentResponse = boardService.createComment(boardId, CreateCommentRequest.builder()
                .content("추천할게요")
                .userId(1L)
                .build());

        // then
        assertThat(createCommentResponse).usingRecursiveComparison().isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("댓글 수정")
    void updateComment() {
        // given
        Long boardId = new Random().nextLong();
        Long commentId = new Random().nextLong();
        User user = User.builder()
                .id(1L).username("최성욱").email("asd@naver.com")
                .password("1234").birth("950928").phoneNumber("010-1234-1234").boards(List.of())
                .build();
        Board tempBoard = Board.builder()
                .id(boardId).title("제목입니다").content("내용입니다")
                .likes(1L).dislikes(2L).movieType(DRAMA_DOCUMENTARY).user(user)
                .comments(new ArrayList<>(List.of(Comment.builder()
                        .id(commentId).content("새롭게 재밌다").username("최성욱").userId(1L)
                        .build())))
                .build();
        given(boardRepository.findById(any())).willReturn(ofNullable(tempBoard));

        Board board = Board.builder()
                .id(boardId).title("제목입니다").content("내용입니다")
                .likes(1L).dislikes(2L).movieType(DRAMA_DOCUMENTARY).user(user)
                .comments(List.of(Comment.builder()
                        .id(commentId).content("추천할게요").username("최성욱").board(tempBoard).userId(1L)
                        .build()))
                .build();
        given(boardRepository.save(any())).willReturn(board);

        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest("추천할게요.", 1L);
        UpdateCommentResponse expectedValue = UpdateCommentResponse.from(CommentPayload.from(
                Comment.builder()
                        .id(commentId).content("추천할게요").username("최성욱").board(board).userId(1L)
                        .build()));

        // when
        UpdateCommentResponse updateCommentResponse = boardService.updateComment(boardId, commentId, updateCommentRequest);

        // then
        assertThat(updateCommentResponse).usingRecursiveComparison().isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() {
        // given
        Long boardId = new Random().nextLong();
        Long commentId = new Random().nextLong();
        User user = User.builder()
                .id(1L).username("최성욱").email("asd@gmail.com")
                .password("1234").birth("950928").phoneNumber("010-1234-1234")
                .boards(List.of())
                .build();
        Board tempBoard = Board.builder()
                .id(boardId).title("극한직업").content("재밌다")
                .likes(1L).dislikes(1L).movieType(DRAMA_DOCUMENTARY)
                .user(user).comments(new ArrayList<>(List.of(Comment.builder()
                        .id(commentId).content("추천할게요").username("최성욱").userId(1L)
                        .build())))
                .build();
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(tempBoard));

        Board board = Board.builder()
                .id(boardId).title("극한직업").content("재밌다")
                .likes(1L).dislikes(1L).movieType(DRAMA_DOCUMENTARY)
                .user(user).comments(new ArrayList<>(List.of(Comment.builder()
                        .id(commentId).content("추천할게요").username("최성욱").userId(1L)
                        .build())))
                .build();
        given(boardRepository.save(any())).willReturn(board);

        // when
        boolean isDelete = boardService.deleteComment(boardId, commentId, new DeleteCommentRequest(1L));

        // then
        assertThat(isDelete).isTrue();
    }

}