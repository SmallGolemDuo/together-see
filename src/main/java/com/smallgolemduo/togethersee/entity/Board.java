package com.smallgolemduo.togethersee.entity;

import com.smallgolemduo.togethersee.entity.enums.MovieType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long likes;
    private Long dislikes;
    @Enumerated(EnumType.STRING)
    private MovieType movieType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
//    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
//    private List<Comment> comments = new ArrayList<>();

    public void modifyBoardInfo(String title, String content, MovieType movieType) {
        this.title = title;
        this.content = content;
        this.movieType = movieType;
    }

}