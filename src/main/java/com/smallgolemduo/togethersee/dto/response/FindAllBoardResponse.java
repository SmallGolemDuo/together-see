package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class FindAllBoardResponse {

    private List<BoardPayload> boardPayloads;

    public static List<FindAllBoardResponse> from(List<BoardPayload> boardPayloads) {
        return boardPayloads.stream()
                .map(boardPayload -> FindAllBoardResponse.builder()
                        .boardPayloads(Collections.singletonList(boardPayload))
                        .build())
                .collect(Collectors.toList());
    }

}