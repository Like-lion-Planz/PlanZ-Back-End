package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;

@Getter
@NoArgsConstructor //역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDto {

    //sub
    @JsonProperty("id")
    public String id;

    //연결상태
    @JsonProperty("has_signed_up")
    public Boolean hasSignedUp;

    //서비스에 연결 완료된 시간
    @JsonProperty("connected_at")
    public Date connectedAt;

    //카카오싱크 간편가입을 통해 로그인한 시간
    @JsonProperty("synched_at")
    public Date synchedAt;

    //사용자 프로퍼티
    @JsonProperty("properties")
    public HashMap<String, String> properties;

    //카카오 계정 정보
    @JsonProperty("kakao_account")
    public KakaoAccount kakaoAccount;

    //uuid 등 추가 정보
    @JsonProperty("for_partner")
    public Partner partner;
    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class KakaoAccount {

        //프로필 정보 제공 동의 여부
        @JsonProperty("profile_needs_agreement")
        public Boolean isProfileAgree;

        //닉네임 제공 동의 여부
        @JsonProperty("profile_nickname_needs_agreement")
        public Boolean isNickNameAgree;

        //프로필 사진 제공 동의 여부
        @JsonProperty("profile_image_needs_agreement")
        public Boolean isProfileImageAgree;

        //사용자 프로필 정보
        @JsonProperty("profile")
        public Profile profile;

        //이름 제공 동의 여부
        @JsonProperty("name_needs_agreement")
        public Boolean isNameAgree;

        //카카오계정 이름
        @JsonProperty("name")
        public String name;//이메일 제공 동의 여부
        @JsonProperty("email_needs_agreement")
        public Boolean isEmailAgree;

        //이메일이 유효 여부
        @JsonProperty("is_email_valid")
        public Boolean isEmailValid;

        //이메일이 인증 여부
        @JsonProperty("is_email_verified")
        public Boolean isEmailVerified;

        //카카오계정 대표 이메일
        @JsonProperty("email")
        public String email;

        //연령대 제공 동의 여부
        @JsonProperty("age_range_needs_agreement")
        public Boolean isAgeAgree;

        //연령대
        @JsonProperty("age_range")
        public String ageRange;

        //출생 연도 제공 동의 여부
        @JsonProperty("birthyear_needs_agreement")
        public Boolean isBirthYearAgree;

        //출생 연도 (YYYY 형식)
        @JsonProperty("birthyear")
        public String birthYear;
        @JsonProperty("birthday_needs_agreement")
        public Boolean isBirthDayAgree;

        //생일 (MMDD 형식)
        @JsonProperty("birthday")
        public String birthDay;

        //생일 타입
        @JsonProperty("birthday_type")
        public String birthDayType;

        //성별 제공 동의 여부
        @JsonProperty("gender_needs_agreement")
        public Boolean isGenderAgree;

        //성별
        @JsonProperty("gender")
        public String gender;

        //전화번호 제공 동의 여부
        @JsonProperty("phone_number_needs_agreement")
        public Boolean isPhoneNumberAgree;

        //전화번호
        @JsonProperty("phone_number")
        public String phoneNumber;

        //CI 동의 여부
        @JsonProperty("ci_needs_agreement")
        public Boolean isCIAgree;
        //CI, 연계 정보
        @JsonProperty("ci")
        public String ci;

        //CI 발급 시각, UTC
        @JsonProperty("ci_authenticated_at")
        public Date ciCreatedAt;

        @Getter
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public class Profile {

            //닉네임
            @JsonProperty("nickname")
            public String nickName;

            //프로필 미리보기 이미지 URL
            @JsonProperty("thumbnail_image_url")
            public String thumbnailImageUrl;

            //프로필 사진 URL
            @JsonProperty("profile_image_url")
            public String profileImageUrl;

            @JsonProperty("is_default_image")
            public String isDefaultImage;
            @JsonProperty("is_default_nickname")
            public Boolean isDefaultNickName;

        }
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Partner {
        @JsonProperty("uuid")
        public String uuid;
    }

}
