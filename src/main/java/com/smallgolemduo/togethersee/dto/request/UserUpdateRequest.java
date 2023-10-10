package com.smallgolemduo.togethersee.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private String password;
    private String phoneNumber;

}