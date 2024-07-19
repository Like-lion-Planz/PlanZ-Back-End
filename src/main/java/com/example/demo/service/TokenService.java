package com.example.demo.service;

import com.example.demo.domain.Token;
import com.example.demo.dto.TokenDto;
import com.example.demo.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private TokenRepository tokenRepository;

    public void saveToken(TokenDto tokenDto){
        Token token = new Token();
        token.setUserId(tokenDto.getUserId());
        token.setToken(tokenDto.getToken());
        tokenRepository.save(token);
    }
}
