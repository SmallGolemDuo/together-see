package com.smallgolemduo.togethersee.dto;

import com.smallgolemduo.togethersee.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPayload {

    private Long id;
    private String content;
    private String username;
    private Long boardId;
    private Long userId;

    public static CommentPayload from(Comment comment) {
        return CommentPayload.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUsername())
                .boardId(comment.getBoard().getId())
                .userId(comment.getUserId())
                .build();
    }

}
