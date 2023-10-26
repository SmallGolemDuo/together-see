package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.dto.UserPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindByIdUserResponse {

    private UserPayload userPayload;

    public static FindByIdUserResponse from(UserPayload userPayload) {
        return FindByIdUserResponse.builder()
                .userPayload(userPayload)
                .build();
    }

}
