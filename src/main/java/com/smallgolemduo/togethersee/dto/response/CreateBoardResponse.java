package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardResponse {

    private BoardPayload boardPayload;

    public static CreateBoardResponse from(BoardPayload boardPayload) {
        return CreateBoardResponse.builder()
                .boardPayload(boardPayload)
                .build();
    }

}