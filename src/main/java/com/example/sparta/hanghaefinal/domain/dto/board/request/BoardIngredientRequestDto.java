package com.example.sparta.hanghaefinal.domain.dto.board.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardIngredientRequestDto {
    private Long boardId;
    private String ingredient;
    private String amount;
//    private LocalDateTime modifiedAt;

}
