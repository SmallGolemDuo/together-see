package com.smallgolemduo.togethersee.entity;

import com.smallgolemduo.togethersee.dto.request.UpdateCommentRequest;
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

    private static final int LAST_COMMENT_DEFAULT_VALUE = 1;

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

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComments(Comment comment) {
        comment.setBoard(this);
        this.comments.add(comment);
    }

    public Comment findLastComment() {
        if (comments.isEmpty()) {
            return null;
        }
        return comments.get(comments.size() - LAST_COMMENT_DEFAULT_VALUE);
    }

    public Comment findCommentId(Long commentId) {
        return this.comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElse(null);
    }

    public boolean isUserIdByComments(UpdateCommentRequest updateCommentRequest){
          return this.comments.stream()
                .anyMatch(comment -> comment.getUserId()
                        .equals(updateCommentRequest.getUserId()));
    }

}