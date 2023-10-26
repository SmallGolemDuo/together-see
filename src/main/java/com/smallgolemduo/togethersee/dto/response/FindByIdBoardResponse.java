package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class FindByIdBoardResponse {

    private BoardPayload boardPayload;

    public static FindByIdBoardResponse from(BoardPayload boardPayload) {
        return FindByIdBoardResponse.builder()
                .boardPayload(boardPayload)
                .build();
    }

}