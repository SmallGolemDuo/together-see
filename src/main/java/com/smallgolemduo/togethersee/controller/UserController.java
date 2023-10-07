package com.smallgolemduo.togethersee.controller;

import com.smallgolemduo.togethersee.dto.request.UserCreateRequest;
import com.smallgolemduo.togethersee.dto.response.UserCreateResponse;
import com.smallgolemduo.togethersee.dto.response.UserFindByIdResponse;
import com.smallgolemduo.togethersee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user")
    public UserCreateResponse createUser(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @GetMapping("/api/user/{id}")
    public UserFindByIdResponse findByIdUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/api/user/{userId}")
    public boolean deleteUser(@PathVariable("userId") Long id) {
        return userService.deleteUser(id);
    }

}
