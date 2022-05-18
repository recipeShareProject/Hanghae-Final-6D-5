package com.example.sparta.hanghaefinal.domain.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUpdateResponse {
    private Long boardId;
    private Long reviewId;
    private List<String> saveImages = new ArrayList<>();
}
