package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.request.UserCreateRequest;
import com.smallgolemduo.togethersee.dto.response.UserCreateResponse;
import com.smallgolemduo.togethersee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
    return UserCreateResponse.from(userRepository.save(userCreateRequest.toEntity()));
  }

}
