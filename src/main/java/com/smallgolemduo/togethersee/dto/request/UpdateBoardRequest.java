package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.enums.Genre;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBoardRequest {

    private String title;
    private String content;
    private String author;
    private Genre genre;

}