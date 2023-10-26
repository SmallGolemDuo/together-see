package com.smallgolemduo.togethersee.dto;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.MovieType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardPayload {

    private Long id;
    private String title;
    private String content;
    private Long likes;
    private Long dislikes;
    private MovieType movieType;
    private Long userId;

    public static BoardPayload from(Board board) {
        return BoardPayload.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .likes(board.getLikes())
                .dislikes(board.getDislikes())
                .movieType(board.getMovieType())
                .userId(board.getUser().getId())
                .build();
    }

}