package com.hanghae.justpotluck.domain.ingredient.dto.response;


import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponse {
    private Long ingredientId;
    private String ingredient;
    private String amount;

    public IngredientResponse(Ingredient ingredient) {
        this.ingredientId = ingredient.getIngredientId();
        this.ingredient = ingredient.getIngredient();
        this.amount = ingredient.getAmount();
    }

}
