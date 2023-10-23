package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.Comment;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentResponse {

    private Long id;
    private String content;
    private String author;

    public static CreateCommentResponse from(Comment comment) {
        return CreateCommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getUserId().toString())
                .build();
    }

}
