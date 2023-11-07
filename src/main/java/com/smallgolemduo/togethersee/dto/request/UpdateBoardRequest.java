package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.enums.MovieType;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBoardRequest {

    @Size(min = 2, max = 50, message = "제목은 2-50자 사이입니다.")
    private String title;

    @Size(min = 2, max = 500, message = "내용은 2-500자 사이입니다.")
    private String content;

    @NotNull(message = "장르 선택은 필수 항목 입니다.")
    private MovieType movieType;

    @NotNull(message = "사용자 ID 는 null 일 수 없습니다.")
    private Long userId;

}