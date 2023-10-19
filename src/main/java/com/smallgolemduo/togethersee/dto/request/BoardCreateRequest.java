package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateRequest {

    private static final Long DEFAULT_VALUE = 0L;

    @NotBlank(message = "제목은 필수 입력 항목 입니다.")
    @Size(min = 2, max = 50, message = "제목은 2-50자 사이입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목 입니다.")
    @Size(min = 2, max = 200, message = "제목은 2-200자 사이입니다.")
    private String content;

    @NotBlank(message = "작성자는 필수 입력 항목 입니다.")
    @Size(min = 2, max = 10, message = "제목은 2-10자 사이입니다.")
    private String author;

    @NotNull(message = "장르는 필수 항목 입니다.")
    private Genre genre;

    private Long userId;

    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .likes(DEFAULT_VALUE)
                .dislikes(DEFAULT_VALUE)
                .genre(this.genre)
                .userId(this.userId)
                .build();
    }

}