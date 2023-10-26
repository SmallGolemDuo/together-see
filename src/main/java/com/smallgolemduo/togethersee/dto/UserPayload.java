package com.smallgolemduo.togethersee.dto;

import com.smallgolemduo.togethersee.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPayload {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String birth;
    private String phoneNumber;
    private List<BoardPayload> board;

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .birth(this.birth)
                .phoneNumber(this.phoneNumber)
                .build();
    }

    public static UserPayload from(User user) {
        return UserPayload.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .birth(user.getBirth())
                .phoneNumber(user.getPhoneNumber())
                .board(user.getBoards().stream()
                        .map(BoardPayload::from)
                        .collect(Collectors.toList()))
                .build();
    }

}