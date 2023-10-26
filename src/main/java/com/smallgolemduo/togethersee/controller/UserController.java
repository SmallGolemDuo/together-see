package com.smallgolemduo.togethersee.controller;

import com.smallgolemduo.togethersee.dto.request.UserCreateRequest;
import com.smallgolemduo.togethersee.dto.request.UserUpdateRequest;
import com.smallgolemduo.togethersee.dto.response.CreateUserResponse;
import com.smallgolemduo.togethersee.dto.response.UserFindAllResponse;
import com.smallgolemduo.togethersee.dto.response.FindByIdUserResponse;
import com.smallgolemduo.togethersee.dto.response.UserUpdateResponse;
import com.smallgolemduo.togethersee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user")
    public CreateUserResponse createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @GetMapping("/api/user/{userId}")
    public FindByIdUserResponse findById(@PathVariable("userId") Long id) {
        return FindByIdUserResponse.from(userService.findById(id));
    }

    @PutMapping("/api/user/{userId}")
    public UserUpdateResponse updateUser(@PathVariable("userId") Long id,
                                         @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(id, userUpdateRequest);
    }

    @DeleteMapping("/api/user/{userId}")
    public boolean deleteUser(@PathVariable("userId") Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/api/user")
    public List<UserFindAllResponse> findAllUsers() {
        return userService.findAll();
    }

}
