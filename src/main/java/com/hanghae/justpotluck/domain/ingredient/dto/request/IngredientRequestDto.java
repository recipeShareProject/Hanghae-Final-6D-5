package com.hanghae.justpotluck.domain.ingredient.dto.request;

import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientRequestDto {

    private List<Ingredient> ingredients;
}
