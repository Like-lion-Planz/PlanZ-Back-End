package com.example.demo.controller;

import com.example.demo.dto.TokenDto;
import com.example.demo.service.TokenService;
import com.google.api.client.auth.oauth2.TokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> saveToekn(@RequestBody TokenDto tokenDto){
        tokenService.saveToken(tokenDto);
        return ResponseEntity.ok().build();
    }
}
