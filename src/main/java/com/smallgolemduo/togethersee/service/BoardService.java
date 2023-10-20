package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.response.BoardFindByIdResponse;
import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.dto.request.BoardCreateRequest;
import com.smallgolemduo.togethersee.dto.response.BoardCreateResponse;
import com.smallgolemduo.togethersee.dto.response.BoardFindAllResponse;
import com.smallgolemduo.togethersee.dto.request.BoardUpdateRequest;
import com.smallgolemduo.togethersee.dto.response.BoardUpdateResponse;
import com.smallgolemduo.togethersee.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardCreateResponse create(BoardCreateRequest boardCreateRequest) {
        return BoardCreateResponse.from(boardRepository.save(boardCreateRequest.toEntity()));
    }

    @Transactional(readOnly = true)
    public BoardFindByIdResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        return BoardFindByIdResponse.from(board);
    }

    @Transactional(readOnly = true)
    public List<BoardFindAllResponse> findAll() {
        List<Board> users = boardRepository.findAll();
        return BoardFindAllResponse.fromList(users);
    }

    @Transactional
    public BoardUpdateResponse updateBoard(Long id, BoardUpdateRequest boardUpdateRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        board.modifyBoardInfo(
                boardUpdateRequest.getTitle(), boardUpdateRequest.getContent(),
                boardUpdateRequest.getAuthor(), boardUpdateRequest.getGenre());
        return BoardUpdateResponse.from(boardRepository.save(board));
    }

    @Transactional
    public boolean delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        boardRepository.delete(board);
        return true;
    }

}