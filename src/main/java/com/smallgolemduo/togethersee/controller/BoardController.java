package com.smallgolemduo.togethersee.controller;

import com.smallgolemduo.togethersee.dto.response.BoardFindByIdResponse;
import com.smallgolemduo.togethersee.dto.request.BoardCreateRequest;
import com.smallgolemduo.togethersee.dto.response.BoardCreateResponse;
import com.smallgolemduo.togethersee.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smallgolemduo.togethersee.dto.response.BoardFindAllResponse;

import java.util.List;

import com.smallgolemduo.togethersee.dto.request.BoardUpdateRequest;
import com.smallgolemduo.togethersee.dto.response.BoardUpdateResponse;

@RestController
@RequestMapping(value = "/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public BoardCreateResponse createBoard(@RequestBody BoardCreateRequest boardCreateRequest) {
        return boardService.createBoard(boardCreateRequest);
    }

    @GetMapping("/{boardId}")
    public BoardFindByIdResponse findById(@PathVariable("boardId") Long id) {
        return boardService.findById(id);
    }

    @GetMapping
    public List<BoardFindAllResponse> findAll() {
        return boardService.findAll();
    }

    @PutMapping("{userId}")
    public BoardUpdateResponse updateBoard(@PathVariable("userId") Long id,
                                           @RequestBody BoardUpdateRequest boardUpdateRequest) {
        return boardService.updateBoard(id, boardUpdateRequest);
    }

}