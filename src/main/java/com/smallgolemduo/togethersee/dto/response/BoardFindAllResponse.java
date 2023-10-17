package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.User;
import com.smallgolemduo.togethersee.entity.enums.Genre;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardFindAllResponse {

    private String title;
    private String content;
    private String author;
    private Long likes;
    private Long dislikes;
    private Genre genre;

    public static BoardFindAllResponse from(Board board) {
        return BoardFindAllResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .likes(board.getLikes())
                .dislikes(board.getDislikes())
                .genre(board.getGenre())
                .build();
    }

    public static List<BoardFindAllResponse> fromList(List<Board> boards) {
        return boards.stream()
                .map(BoardFindAllResponse::from)
                .collect(Collectors.toList());
    }

}