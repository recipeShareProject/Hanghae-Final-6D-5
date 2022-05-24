package com.hanghae.justpotluck.domain.board.dto.request;

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
public class BoardUpdateRequestDto {
    private Long boardId;
    private String title;
    private String quantity;
    //    private FoodCategory category;
    private String category;
    private ArrayList<String> processList;
    private List<String> saveImageUrl = new ArrayList<>();
    private List<MultipartFile> images = new ArrayList<>();
//    private List<Ingredient> ingredient;
//    private LocalDateTime modifiedAt;

}
