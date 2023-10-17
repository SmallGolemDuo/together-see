package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.response.BoardFindByIdResponse;
import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.dto.request.BoardCreateRequest;
import com.smallgolemduo.togethersee.dto.response.BoardCreateResponse;
import com.smallgolemduo.togethersee.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardCreateResponse createBoard(BoardCreateRequest boardCreateRequest) {
        return BoardCreateResponse.from(boardRepository.save(boardCreateRequest.toEntity()));
    }

    @Transactional(readOnly = true)
    public BoardFindByIdResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        return BoardFindByIdResponse.from(board);
    }

}