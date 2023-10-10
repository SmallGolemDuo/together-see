package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponse {

    private String username;
    private String email;
    private String password;
    private String birth;
    private String phoneNumber;

    public static UserUpdateResponse from(User user) {
        return UserUpdateResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .birth(user.getBirth())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

}
