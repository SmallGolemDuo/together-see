package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.request.UpdateBoardRequest;
import com.smallgolemduo.togethersee.dto.response.UpdateBoardResponse;
import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.Genre;
import com.smallgolemduo.togethersee.repository.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Test
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

}