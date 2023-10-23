package com.smallgolemduo.togethersee.repository;

import com.smallgolemduo.togethersee.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
