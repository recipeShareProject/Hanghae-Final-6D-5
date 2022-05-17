package com.example.sparta.hanghaefinal.domain.dto.review;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewSaveRequestDto {
    private Long boardId;
    private Long reviewId;
    private String contents;
    private String nickname;
    private List<MultipartFile> images = new ArrayList<>();

}
