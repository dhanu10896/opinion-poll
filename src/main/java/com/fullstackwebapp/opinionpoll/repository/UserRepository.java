package com.fullstackwebapp.opinionpoll.repository;

import com.fullstackwebapp.opinionpoll.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface  UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsernameOrEmail(String username, String email);

    List<User> findByIdIn(List<Long> userIds);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
