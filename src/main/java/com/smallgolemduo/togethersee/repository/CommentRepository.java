package com.smallgolemduo.togethersee.repository;

import com.smallgolemduo.togethersee.entity.Board;
import com.smallgolemduo.togethersee.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoard(Board board);

}
