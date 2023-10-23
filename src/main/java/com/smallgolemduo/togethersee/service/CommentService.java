package com.smallgolemduo.togethersee.service;

import com.smallgolemduo.togethersee.dto.request.CreateCommentRequest;
import com.smallgolemduo.togethersee.dto.response.CreateCommentResponse;
import com.smallgolemduo.togethersee.dto.response.FindByIdBoardResponse;
import com.smallgolemduo.togethersee.dto.response.FindByIdCommentResponse;
import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.Comment;
import com.smallgolemduo.togethersee.entity.User;
import com.smallgolemduo.togethersee.repository.BoardRepository;
import com.smallgolemduo.togethersee.repository.CommentRepository;
import com.smallgolemduo.togethersee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResponse createComment(Long id, CreateCommentRequest createCommentRequest) {
        User user = userRepository.findById(createCommentRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 정보가 없습니다."));
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));

        Comment comment = createCommentRequest.toEntity();
        comment.setAuthor(user.getUsername());
        comment.setUserId(comment.getUserId());
        comment.setBoard(board);
        Comment savedComment = commentRepository.save(comment);

        CreateCommentResponse response = CreateCommentResponse.from(savedComment);
        return response;
    }

    @Transactional(readOnly = true)
    public FindByIdCommentResponse findByIdComment(Long id, Long commentId) {
        boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("작성된 게시물이 없습니다."));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("작성된 댓글이 없습니다."));
        return FindByIdCommentResponse.from(comment);
    }

}