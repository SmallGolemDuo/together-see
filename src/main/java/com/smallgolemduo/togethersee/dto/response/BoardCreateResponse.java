package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.Genre;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateResponse {

    private String title;
    private String content;
    private String author;
    private Long likes;
    private Long dislikes;
    private Genre genre;

    public static BoardCreateResponse from(Board board) {
        return BoardCreateResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .likes(board.getLikes())
                .dislikes(board.getDislikes())
                .genre(board.getGenre())
                .build();
    }

}
