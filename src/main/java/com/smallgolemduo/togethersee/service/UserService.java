package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.request.UserCreateRequest;
import com.smallgolemduo.togethersee.dto.request.UserUpdateRequest;
import com.smallgolemduo.togethersee.dto.response.UserCreateResponse;
import com.smallgolemduo.togethersee.dto.response.UserFindByIdResponse;
import com.smallgolemduo.togethersee.dto.response.UserUpdateResponse;
import com.smallgolemduo.togethersee.entity.User;
import com.smallgolemduo.togethersee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {
        return UserCreateResponse.from(userRepository.save(userCreateRequest.toEntity()));
    }

    public UserFindByIdResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));
        return UserFindByIdResponse.from(user);
    }

    @Transactional
    public UserUpdateResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));
        user.modifyUserInfo(userUpdateRequest.getPassword(), userUpdateRequest.getPhoneNumber());
        return UserUpdateResponse.from(userRepository.save(user));
    }

    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));
        userRepository.delete(user);
        return true;
    }

}
