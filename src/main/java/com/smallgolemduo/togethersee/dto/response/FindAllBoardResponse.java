package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.MovieType;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllBoardResponse {

    private String title;
    private String content;
    private Long likes;
    private Long dislikes;
    private MovieType movieType;

    public static FindAllBoardResponse from(Board board) {
        return FindAllBoardResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .likes(board.getLikes())
                .dislikes(board.getDislikes())
                .movieType(board.getMovieType())
                .build();
    }

    public static List<FindAllBoardResponse> fromList(List<Board> boards) {
        return boards.stream()
                .map(FindAllBoardResponse::from)
                .collect(Collectors.toList());
    }

}