package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {

    @NotBlank(message = "내용은 필수 입력 항목 입니다.")
    @Size(min = 2, max = 200, message = "제목은 2-200자 사이입니다.")
    private String content;

    @NotBlank(message = "작성자는 필수 입력 항목 입니다.")
    @Size(min = 2, max = 10, message = "제목은 2-10자 사이입니다.")
    private String author;

    private Long userId;

    private Board board;

    public Comment toEntity() {
        return Comment.builder()
                .content(this.content)
                .author(this.author)
                .userId(this.userId)
                .board(this.board)
                .build();
    }

}
