package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserTokenDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${refresh.secret}")
    private String secretKet2;

    private final UserRepository userRepository;
    private Long expiredMs = 1000 * 60 * 60L;

    public UserTokenDto publish_token(User user) {
        UserTokenDto tokenDto = new UserTokenDto();
        tokenDto.setAccessToken(JwtTokenProvider.createJwt(user, secretKey, expiredMs));
        tokenDto.setRefreshToken(JwtTokenProvider.createRefreshToken(user, secretKet2, expiredMs));
        tokenDto.setGrantType("Bearer");
        tokenDto.setExpiresIn(expiredMs);
        return tokenDto;
    }

    public Optional<User> findBySub(String sub){

        return Optional.ofNullable(userRepository.findBySub(sub));
    }
    public User findByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userSub = authentication.getName();
        return userRepository.findBySub(userSub);

    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
}
