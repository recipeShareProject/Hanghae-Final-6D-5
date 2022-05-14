package com.example.sparta.hanghaefinal.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReIssueRequestDto {
    String email;
    String refreshToken;
}
