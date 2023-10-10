package com.smallgolemduo.togethersee.dto.response;

import com.smallgolemduo.togethersee.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFindAllResponse {

    private String username;
    private String email;
    private String password;
    private String birth;
    private String phoneNumber;

    public static UserFindAllResponse from(User user) {
        return new UserFindAllResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getBirth(),
                user.getPhoneNumber());
    }

    public static List<UserFindAllResponse> fromList(List<User> users) {
        return users.stream()
                .map(UserFindAllResponse::from)
                .collect(Collectors.toList());
    }

}
