package com.example.sparta.hanghaefinal.domain.dto.community;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateDto {
    private String category;

    @Builder
    public PostUpdateDto(String category) {
        this.category = category;
    }
}
