package com.smallgolemduo.togethersee.controller;

import com.smallgolemduo.togethersee.dto.request.*;
import com.smallgolemduo.togethersee.dto.response.*;
import com.smallgolemduo.togethersee.dto.response.FindByIdBoardResponse;
import com.smallgolemduo.togethersee.dto.response.CreateBoardResponse;
import com.smallgolemduo.togethersee.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smallgolemduo.togethersee.dto.response.FindAllBoardResponse;

import java.util.List;

import com.smallgolemduo.togethersee.dto.response.UpdateBoardResponse;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public CreateBoardResponse create(@RequestBody @Valid CreateBoardRequest createBoardRequest) {
        return boardService.create(createBoardRequest);
    }

    @GetMapping("/{boardId}")
    public FindByIdBoardResponse findById(@PathVariable("boardId") Long boardId) {
        return boardService.findById(boardId);
    }

    @GetMapping
    public List<FindAllBoardResponse> findAll() {
        return boardService.findAll();
    }

    @PutMapping("/{boardId}")
    public UpdateBoardResponse update(@PathVariable("boardId") Long boardId,
                                      @RequestBody @Valid UpdateBoardRequest updateBoardRequest) {
        return boardService.update(boardId, updateBoardRequest);
    }

    @DeleteMapping("/{boardId}")
    public boolean deleted(@PathVariable("boardId") Long id) {
        return boardService.deleted(id);
    }

    @PostMapping("/{boardId}/comments")
    public CreateCommentResponse createComment(@PathVariable("boardId") Long id,
                                               @RequestBody @Valid CreateCommentRequest createCommentRequest) {
        return boardService.createComment(id, createCommentRequest);
    }

    @PutMapping("/{boardId}/comments/{commentId}")
    public UpdateCommentResponse updateComment(@PathVariable("boardId") Long id,
                                               @PathVariable("commentId") Long commentId,
                                               @RequestBody @Valid UpdateCommentRequest updateCommentRequest) {
        return boardService.updateComment(id, commentId, updateCommentRequest);
    }

    @DeleteMapping("/{boardId}/comments/{commentId}")
    public boolean deleteComment(@PathVariable("boardId") Long boardId,
                                 @PathVariable("commentId") Long commentId,
                                 @RequestBody DeleteCommentRequest deleteCommentRequest) {
        return boardService.deleteComment(boardId, commentId, deleteCommentRequest);
    }

}