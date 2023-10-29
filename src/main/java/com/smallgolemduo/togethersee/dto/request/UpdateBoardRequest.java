package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.MovieType;
import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBoardRequest {

    private static final Long DEFAULT_VALUE = 0L;

    @Size(min = 2, max = 50, message = "제목은 2-50자 사이입니다.")
    private String title;

    @Size(min = 2, max = 500, message = "내용은 2-500자 사이입니다.")
    private String content;

    private MovieType movieType;

}