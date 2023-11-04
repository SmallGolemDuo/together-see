package com.smallgolemduo.togethersee.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCommentRequest {

    @NotNull(message = "사용자 ID 는 null 일 수 없습니다.")
    private Long userId;

}
