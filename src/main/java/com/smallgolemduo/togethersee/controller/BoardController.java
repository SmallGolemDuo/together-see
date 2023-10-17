package com.smallgolemduo.togethersee.controller;

import com.smallgolemduo.togethersee.dto.request.BoardCreateRequest;
import com.smallgolemduo.togethersee.dto.response.BoardCreateResponse;
import com.smallgolemduo.togethersee.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.smallgolemduo.togethersee.dto.response.BoardFindByIdResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public BoardCreateResponse createBoard(@RequestBody BoardCreateRequest boardCreateRequest) {
        return boardService.createBoard(boardCreateRequest);
    }

    @GetMapping("/{userId}")
    public BoardFindByIdResponse findById(@PathVariable("userId") Long id) {
        return boardService.findById(id);
    }

}