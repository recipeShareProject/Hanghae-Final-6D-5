package com.hanghae.justpotluck.domain.board.dto.request;

import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import com.hanghae.justpotluck.domain.process.entity.RecipeProcess;
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
    private List<RecipeProcess> processes = new ArrayList<>();
    private String quantity;
    private List<Ingredient> ingredients = new ArrayList<>();
    private String category;
    private String cookTime;
    private int viewCount;
    private boolean bookmark;
    private List<String> saveImageUrl;
//    private List<MultipartFile> processImages = new ArrayList<>();
    private List<MultipartFile> completeImages = new ArrayList<>();
}
