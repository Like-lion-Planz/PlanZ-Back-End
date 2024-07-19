package com.example.demo.repository;

import com.example.demo.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    String findTokenById(Long userId);
}
