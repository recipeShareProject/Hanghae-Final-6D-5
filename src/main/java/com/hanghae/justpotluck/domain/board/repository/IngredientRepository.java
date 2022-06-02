package com.hanghae.justpotluck.domain.board.repository;

import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
