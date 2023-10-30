package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.dto.CommentPayload;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentResponse {

    private CommentPayload commentPayload;

    public static CreateCommentResponse from(CommentPayload commentPayload) {
        return CreateCommentResponse.builder()
                .commentPayload(commentPayload)
                .build();
    }

}
