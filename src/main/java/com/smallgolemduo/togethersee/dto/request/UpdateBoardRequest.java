package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.enums.MovieType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBoardRequest {

    private String title;
    private String content;
    private MovieType movieType;

}