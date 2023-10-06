package com.smallgolemduo.togethersee.repository;

import com.smallgolemduo.togethersee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
