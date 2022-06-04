package com.hanghae.justpotluck.domain.ingredient.dto.response;

import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientSearchResponse {
    private Long ingredientId;
    private ArrayList<String> include;
    private ArrayList<String> exclude;

    public IngredientSearchResponse(Ingredient ingredient) {
        this.ingredientId = ingredient.getIngredientId();
//        this.include = ingredient.getInclude();
//        this.exclude = ingredient.getExclude();
    }

}