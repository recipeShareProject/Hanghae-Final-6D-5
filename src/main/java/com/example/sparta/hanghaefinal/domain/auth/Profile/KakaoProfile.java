package com.example.sparta.hanghaefinal.domain.auth.Profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoProfile {
    KakaoAccount kakao_account;

    @Data
    public class KakaoAccount {
        private String email;
//        private String picture;
    }
}
