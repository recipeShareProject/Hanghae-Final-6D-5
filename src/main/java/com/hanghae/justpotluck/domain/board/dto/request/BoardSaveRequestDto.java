package com.hanghae.justpotluck.domain.board.dto.request;

import com.hanghae.justpotluck.domain.board.entity.Ingredient;
import com.hanghae.justpotluck.domain.board.entity.RecipeProcess;
import com.hanghae.justpotluck.domain.board.entity.FoodCategory;
import com.hanghae.justpotluck.domain.user.entity.User;
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
    private List<RecipeProcess> processList;
    private List<Ingredient> ingredientList;
    private FoodCategory category;
    private User user;
//    private String nickname;
    private String cookingTime;
    private int viewCount;
    private boolean bookmark;
    private List<MultipartFile> images = new ArrayList<>();
}
