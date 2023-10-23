package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindByIdCommentResponse {

    private Long id;
    private String content;
    private String author;

    public static FindByIdCommentResponse from(Comment comment) {
        return FindByIdCommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getUserId().toString())
                .build();
    }

}