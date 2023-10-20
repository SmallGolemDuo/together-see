package com.smallgolemduo.togethersee.entity;

import com.smallgolemduo.togethersee.entity.enums.Genre;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String author;
    private Long likes;
    private Long dislikes;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Long userId;

    public void modifyBoardInfo(String title, String content, String author, Genre genre) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
        if (author != null) {
            this.author = author;
        }
        if (genre != null) {
            this.genre = genre;
        }
    }

}