package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBoardResponse {

    private BoardPayload boardPayload;

    public static UpdateBoardResponse from(BoardPayload boardPayload) {
        return UpdateBoardResponse.builder()
                .boardPayload(boardPayload)
                .build();
    }

}