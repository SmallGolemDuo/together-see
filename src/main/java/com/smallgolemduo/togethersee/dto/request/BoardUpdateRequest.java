package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.enums.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
public class BoardUpdateRequest {

    @NotBlank(message = "제목은 필수 항목 입니다.")
    @Size(min = 2,max = 50, message = "제목은 2-50자 사이입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 항목 입니다.")
    @Size(min = 2,max = 200, message = "제목은 2-200자 사이입니다.")
    private String content;

    @NotBlank(message = "작성자는 필수 항목 입니다.")
    @Size(min = 2,max = 10, message = "제목은 2-10자 사이입니다.")
    private String author;

    @NotNull(message = "장르는 필수 항목 입니다.")
    private Genre genre;

}