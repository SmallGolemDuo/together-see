package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.BoardPayload;
import com.smallgolemduo.togethersee.dto.CommentPayload;
import com.smallgolemduo.togethersee.dto.UserPayload;
import com.smallgolemduo.togethersee.dto.request.*;
import com.smallgolemduo.togethersee.dto.response.*;
import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.Comment;
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
    public FindByIdBoardResponse findById(Long boardId) {
        Board board = boardRepository.findById(boardId)
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
    public UpdateBoardResponse update(Long boardId, UpdateBoardRequest updateBoardRequest) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        if (!board.getUser().isUserIdByBoard(updateBoardRequest)) {
            throw new IllegalArgumentException("게시글 수정에 대한 권한이 없습니다.");
        }
        if (updateBoardRequest.getTitle() != null) {
            board.setTitle(updateBoardRequest.getTitle());
        }
        if (updateBoardRequest.getContent() != null) {
            board.setContent(updateBoardRequest.getContent());
        }
        if (updateBoardRequest.getMovieType() != null) {
            board.setMovieType(updateBoardRequest.getMovieType());
        }
        board.getUser().addBoards(board);
        return UpdateBoardResponse.from(BoardPayload.from(boardRepository.save(board)));
    }

    @Transactional
    public boolean deleted(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        boardRepository.delete(board);
        return true;
    }

    @Transactional
    public CreateCommentResponse createComment(Long id, CreateCommentRequest createCommentRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        UserPayload userPayload = userService.findById(createCommentRequest.getUserId());
        Comment comment = createCommentRequest.toEntity();
        comment.addUsername(userPayload.getUsername());
        board.addComments(comment);
        board = boardRepository.save(board);
        return CreateCommentResponse.from(CommentPayload.from(board.findLastComment()));
    }

    @Transactional
    public UpdateCommentResponse updateComment(Long boardId, Long commentId, UpdateCommentRequest updateCommentRequest) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        Comment comment = board.findCommentId(commentId);
        if (comment == null) {
            throw new IllegalArgumentException("작성된 댓글이 없습니다.");
        }
        if (!board.isUserIdByComments(updateCommentRequest)) {
            throw new IllegalArgumentException("댓글 수정에 대한 권한이 없습니다.");
        }
        if (updateCommentRequest.getContent() != null) {
            comment.setContent(updateCommentRequest.getContent());
        }
        board.addComments(comment);
        board = boardRepository.save(board);
        return UpdateCommentResponse.from(CommentPayload.from(board.findLastComment()));
    }

    @Transactional
    public boolean deleteComment(Long boardId, Long commentId, DeleteCommentRequest deleteCommentRequest) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));
        boolean isComment = board.getComments().stream()
                .anyMatch(c -> c.getId().equals(commentId) && c.getUserId().equals(deleteCommentRequest.getUserId()));
        if (!isComment) {
            throw new IllegalArgumentException("해당하는 댓글이 없거나 댓글을 작성한 사용자가 아닙니다.");
        }
        board.getComments().removeIf(c -> c.getId().equals(commentId));
        boardRepository.save(board);
        return true;
    }

}