package com.example.sparta.hanghaefinal.web.dto;

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
