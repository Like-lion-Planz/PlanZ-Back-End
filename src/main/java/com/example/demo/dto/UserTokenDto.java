package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenDto {
    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;
}
