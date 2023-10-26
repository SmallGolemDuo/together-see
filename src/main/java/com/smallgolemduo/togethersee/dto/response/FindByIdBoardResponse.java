package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.MovieType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class FindByIdBoardResponse {

    private Long id;
    private String title;
    private String content;
    private Long likes;
    private Long dislikes;
    private MovieType movieType;

    public static FindByIdBoardResponse from(Board board) {
        return FindByIdBoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .likes(board.getLikes())
                .dislikes(board.getDislikes())
                .movieType(board.getMovieType())
                .build();
    }

}