package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.request.UserCreateRequest;
import com.smallgolemduo.togethersee.dto.response.UserCreateResponse;
import com.smallgolemduo.togethersee.entity.User;
import com.smallgolemduo.togethersee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {

    User user = User.builder()
        .username(userCreateRequest.getUsername())
        .email(userCreateRequest.getEmail())
        .password(userCreateRequest.getPassword())
        .birth(userCreateRequest.getBirth())
        .phoneNumber(userCreateRequest.getPhoneNumber())
        .build();

    userRepository.save(user);

    return UserCreateResponse.from(User.builder()
        .username(userCreateRequest.getUsername())
        .email(userCreateRequest.getEmail())
        .password(userCreateRequest.getPassword())
        .birth(userCreateRequest.getBirth())
        .phoneNumber(userCreateRequest.getPhoneNumber())
        .build());
  }

}
