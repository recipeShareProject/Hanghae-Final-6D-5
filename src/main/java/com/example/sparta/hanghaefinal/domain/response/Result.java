package com.example.sparta.hanghaefinal.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Result implements Serializable {
    private boolean success; // 응답 성공 여부
    private int code; // 응답 코드
    private String msg; // 응답 메시지
}
