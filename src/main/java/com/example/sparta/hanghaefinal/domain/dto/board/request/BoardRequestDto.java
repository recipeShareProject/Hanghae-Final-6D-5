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
public class BoardRequestDto {
    private String title;
    private String quantity;
    private String cookingTime;
    private String nation;
    private List<String> process;
    private List<String> ingredient;
    private List<String> amount;
//    private int viewCount;
//    private boolean bookmark;
    private List<String> images = new ArrayList<>();
}
