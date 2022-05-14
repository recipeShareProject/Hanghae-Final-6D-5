package com.example.sparta.hanghaefinal.domain.dto.board.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String contents;
    private String nickname;
    private int view;
    private boolean bookmark;
    private List<MultipartFile> images = new ArrayList<>();
}
