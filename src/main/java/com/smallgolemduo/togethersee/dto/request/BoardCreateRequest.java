package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.User;
import com.smallgolemduo.togethersee.entity.enums.Genre;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {

    private String title;
    private String content;
    private String author;
    private Long likes;
    private Long dislikes;
    private Genre genre;
    private User user;

    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .likes(this.likes)
                .dislikes(this.dislikes)
                .genre(this.genre)
                .user(this.user)
                .build();
    }

}
