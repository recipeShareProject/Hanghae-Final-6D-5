package com.hanghae.justpotluck.domain.board.dto.response.board;

import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import com.hanghae.justpotluck.domain.process.entity.RecipeProcess;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto extends Timestamped {
    private Long id;
    private String title;
    //    private List<RecipeProcess> process;
//    private List<String> ingredients;
    private List<RecipeProcess> processes;
    private List<Ingredient> ingredients;
    private String category;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String cookTime;
    private List<String> completeImages;
    private List<Review> reviews;
    private String quantity;
    private int viewCount;

    public BoardResponseDto(Board entity, List<String> completeImages) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.processes = entity.getProcesses();
        this.ingredients = entity.getIngredients();
        this.cookTime = entity.getCookTime();
        this.writer = entity.getUser().getName();
        this.quantity = entity.getQuantity();
        this.viewCount = entity.getViewCount();
        this.completeImages = completeImages;
        this.reviews = entity.getReviewList();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }


    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.processes = entity.getProcesses();
        this.ingredients = entity.getIngredients();
        this.cookTime = entity.getCookTime();
        this.writer = entity.getUser().getName();
        this.quantity = entity.getQuantity();
        this.viewCount = entity.getViewCount();
//        this.completeImages = completeImages;
        this.reviews = entity.getReviewList();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
