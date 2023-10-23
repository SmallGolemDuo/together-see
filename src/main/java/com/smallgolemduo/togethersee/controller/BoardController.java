package com.smallgolemduo.togethersee.controller;

import com.smallgolemduo.togethersee.dto.request.CreateCommentRequest;
import com.smallgolemduo.togethersee.dto.response.FindByIdCommentResponse;
import com.smallgolemduo.togethersee.dto.response.*;
import com.smallgolemduo.togethersee.dto.request.BoardCreateRequest;
import com.smallgolemduo.togethersee.service.BoardService;
import com.smallgolemduo.togethersee.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.smallgolemduo.togethersee.dto.request.UpdateBoardRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping
    public BoardCreateResponse create(@RequestBody @Valid BoardCreateRequest boardCreateRequest) {
        return boardService.create(boardCreateRequest);
    }

    @GetMapping("/{boardId}")
    public FindByIdBoardResponse findById(@PathVariable("boardId") Long id) {
        return boardService.findById(id);
    }

    @GetMapping
    public List<BoardFindAllResponse> findAll() {
        return boardService.findAll();
    }

    @PutMapping("{boardId}")
    public UpdateBoardResponse update(@PathVariable("boardId") Long id,
                                      @RequestBody UpdateBoardRequest updateBoardRequest) {
        return boardService.update(id, updateBoardRequest);
    }

    @DeleteMapping("/{boardId}")
    public boolean deleted(@PathVariable("boardId") Long id) {
        return boardService.deleted(id);
    }

    @PostMapping("/{boardId}/comments")
    public CreateCommentResponse createComment(@PathVariable("boardId") Long id,
                                               @RequestBody @Valid CreateCommentRequest createCommentRequest) {
        return commentService.createComment(id, createCommentRequest);
    }

    @GetMapping("/{boardId}/comments/{commentId}")
    public FindByIdCommentResponse findByIdComment(@PathVariable("boardId") Long id,
                                                   @PathVariable("commentId") Long commentId) {
        return commentService.findByIdComment(id, commentId);
    }

}