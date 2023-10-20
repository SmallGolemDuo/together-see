package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.Genre;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class FindByIdBoardResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private Long likes;
    private Long dislikes;
    private Genre genre;

    public static FindByIdBoardResponse from(Board board) {
        return FindByIdBoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .likes(board.getLikes())
                .dislikes(board.getDislikes())
                .genre(board.getGenre())
                .build();
    }

}