package com.smallgolemduo.togethersee.dto.request;

import com.smallgolemduo.togethersee.entity.User;
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
public class UserCreateRequest {

    private String username;
    private String email;
    private String password;
    private String birth;
    private String phoneNumber;

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .birth(this.birth)
                .phoneNumber(this.phoneNumber)
                .build();
    }

}
