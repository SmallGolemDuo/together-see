package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.UserPayload;
import com.smallgolemduo.togethersee.dto.request.UserCreateRequest;
import com.smallgolemduo.togethersee.dto.request.UserUpdateRequest;
import com.smallgolemduo.togethersee.dto.response.*;
import com.smallgolemduo.togethersee.entity.User;
import com.smallgolemduo.togethersee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse createUser(UserCreateRequest userCreateRequest) {
        return CreateUserResponse.from(userRepository.save(userCreateRequest.toEntity()));
    }

    @Transactional(readOnly = true)
    public UserPayload findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));
        return UserPayload.from(user);
    }

    @Transactional(readOnly = true)
    public List<UserFindAllResponse> findAll() {
        List<User> users = userRepository.findAll();
        return UserFindAllResponse.fromList(users);
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
