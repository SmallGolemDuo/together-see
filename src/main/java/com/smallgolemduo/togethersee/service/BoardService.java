package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import com.smallgolemduo.togethersee.dto.UserPayload;
import com.smallgolemduo.togethersee.dto.response.*;
import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.dto.request.CreateBoardRequest;
import com.smallgolemduo.togethersee.dto.request.UpdateBoardRequest;
import com.smallgolemduo.togethersee.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserService userService;

    @Transactional
    public CreateBoardResponse create(CreateBoardRequest createBoardRequest) {
        UserPayload userPayload = userService.findById(createBoardRequest.getUserId());
        Board board = createBoardRequest.toEntity();
        board.setUser(userPayload.toEntity());
        return CreateBoardResponse.from(BoardPayload.from(boardRepository.save(board)));
    }

    @Transactional(readOnly = true)
    public FindByIdBoardResponse findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        return FindByIdBoardResponse.from(board);
    }

    @Transactional(readOnly = true)
    public List<FindAllBoardResponse> findAll() {
        List<Board> users = boardRepository.findAll();
        return FindAllBoardResponse.fromList(users);
    }

    @Transactional
    public UpdateBoardResponse updateBoard(Long id, UpdateBoardRequest updateBoardRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        board.modifyBoardInfo(
                updateBoardRequest.getTitle(),
                updateBoardRequest.getContent(),
                updateBoardRequest.getMovieType());
        return UpdateBoardResponse.from(boardRepository.save(board));
    }

    @Transactional
    public boolean deleted(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        boardRepository.delete(board);
        return true;
    }

}