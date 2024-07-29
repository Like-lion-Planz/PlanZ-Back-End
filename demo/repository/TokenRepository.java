package com.example.demo.repository;

import com.example.demo.domain.Token;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    String findTokenById(User user);
}
