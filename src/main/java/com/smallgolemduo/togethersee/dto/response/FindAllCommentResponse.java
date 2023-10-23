package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.Comment;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllCommentResponse {

    private Long id;
    private String content;
    private String author;

    public static FindAllCommentResponse from(Comment comment) {
        return FindAllCommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .build();
    }

    public static List<FindAllCommentResponse> fromList(List<Comment> comments) {
        return comments.stream()
                .map(FindAllCommentResponse::from)
                .collect(Collectors.toList());
    }

}