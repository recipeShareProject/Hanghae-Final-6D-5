package com.hanghae.justpotluck.domain.ingredient.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientSaveRequest {
    private String ingredient;
    private String amount;
}
