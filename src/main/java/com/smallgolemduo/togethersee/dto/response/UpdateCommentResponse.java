package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.dto.CommentPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateCommentResponse {

    private CommentPayload commentPayload;

    public static UpdateCommentResponse from(CommentPayload commentPayload) {
        return UpdateCommentResponse.builder()
                .commentPayload(commentPayload)
                .build();
    }

}