package com.smallgolemduo.togethersee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequest {

    @Size(min = 2, max = 200, message = "제목은 2-200자 사이입니다.")
    private String content;

    private Long userId;

}
