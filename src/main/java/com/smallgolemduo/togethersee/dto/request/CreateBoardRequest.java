package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.enums.MovieType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardRequest {

    public static final Long DEFAULT_VALUE = 0L;

    @NotBlank(message = "제목은 필수 입력 항목 입니다.")
    @Size(min = 2, max = 50, message = "제목은 2-50자 사이입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 항목 입니다.")
    @Size(min = 2, max = 200, message = "제목은 2-200자 사이입니다.")
    private String content;

    @NotNull(message = "장르 선택은 필수 항목 입니다.")
    private MovieType movieType;

    @NotNull(message = "사용자 ID 는 null 일 수 없습니다.")
    private Long userId;

    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .likes(DEFAULT_VALUE)
                .dislikes(DEFAULT_VALUE)
                .movieType(this.movieType)
                .comments(new ArrayList<>())
                .build();
    }

}