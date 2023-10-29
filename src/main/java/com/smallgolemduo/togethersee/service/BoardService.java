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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserService userService;

    private final BoardRepository boardRepository;

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
        return FindByIdBoardResponse.from(BoardPayload.from(board));
    }

    @Transactional(readOnly = true)
    public List<FindAllBoardResponse> findAll() {
        List<Board> boards = boardRepository.findAll();
        List<BoardPayload> boardPayloads = boards.stream()
                .map(BoardPayload::from)
                .collect(Collectors.toList());
        return FindAllBoardResponse.from(boardPayloads);
    }

    @Transactional
    public UpdateBoardResponse update(Long id, UpdateBoardRequest updateBoardRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        if (updateBoardRequest.getTitle() != null) {
            board.setTitle(updateBoardRequest.getTitle());
        }
        if (updateBoardRequest.getContent() != null) {
            board.setContent(updateBoardRequest.getContent());
        }
        if (updateBoardRequest.getMovieType() != null) {
            board.setMovieType(updateBoardRequest.getMovieType());
        }
        return UpdateBoardResponse.from(BoardPayload.from(boardRepository.save(board)));
    }

    @Transactional
    public boolean deleted(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        boardRepository.delete(board);
        return true;
    }

}