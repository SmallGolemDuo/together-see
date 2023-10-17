package com.smallgolemduo.togethersee.repository;

import com.smallgolemduo.togethersee.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}