package com.example.demo.service;

import com.example.demo.domain.Provider;
import com.example.demo.domain.User;
import com.example.demo.dto.TokenDto;
import com.example.demo.dto.UserResponseDto;
import com.example.demo.dto.UserTokenDto;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.jwt.JwtTokenProvider;
import com.example.demo.util.oauth.CustomOauth2UserDetails;
import com.example.demo.util.oauth.GoogleUserDetails;
import com.example.demo.util.oauth.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${refresh.secret}")
    private String secretKet2;

    private Long expiredMs = 1000 * 60 * 60L;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser called with userRequest: {}", userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User loaded: {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        if(provider.equals("google")){
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        User user = userRepository.findByEmail(email);
        System.out.println(user);
        if (user == null) {
            user = User.builder()
                    .sub(providerId)
                    .name(name)
                    .email(email)
                    .provider(Provider.google)
                    .image((String) oAuth2User.getAttributes().get("picture"))
                    .build();
            userRepository.save(user);
            System.out.println(user.getName());
        }
        //토큰 생성
        UserTokenDto userTokenDto = new UserTokenDto();
        userTokenDto.setAccessToken(JwtTokenProvider.createJwt(user, secretKey, expiredMs));
        userTokenDto.setRefreshToken(JwtTokenProvider.createRefreshToken(user, secretKet2, expiredMs));
        userTokenDto.setGrantType("Bearer");
        userTokenDto.setExpiresIn(expiredMs);
        UserResponseDto userResponseDto = new UserResponseDto(user.getId(), user.getName(), user.getEmail(), userTokenDto);
        return new CustomOauth2UserDetails(user, oAuth2User.getAttributes(),userResponseDto);
    }
}
