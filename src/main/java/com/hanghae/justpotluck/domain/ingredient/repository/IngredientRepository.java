package com.hanghae.justpotluck.domain.ingredient.repository;

import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query(value = "select p from Ingredient p where p.include in :ingredients and p.exclude not in :ingredients", nativeQuery = true)
    List<Ingredient> findAllByIngredients(List<String> ingredients);
    Ingredient findByIngredient(String ingredient);

}
