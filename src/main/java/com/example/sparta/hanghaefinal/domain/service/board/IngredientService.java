package com.example.sparta.hanghaefinal.domain.service.board;

import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.board.Ingredient;
import com.example.sparta.hanghaefinal.domain.repository.board.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Transactional
    public void saveRecipe(Board board, List<String> ingredients, List<String> amount){
        for (int i = 0; i < ingredients.size(); i++)
        {
            Ingredient ingredient = Ingredient.builder()
                    .ingredient(ingredients.get(i))
                    .amount(amount.get(i))
                    .board(board)
                    .build();

            ingredientRepository.save(ingredient);
        }
    }

    @Transactional
    public void modifyRecipe(Board board, List<String> ingredients, List<String> amount){
        deleteRecipe(board.getId());
        saveRecipe(board, ingredients, amount);
    }


    @Transactional
    public void deleteRecipe(Long boardId) {ingredientRepository.deleteAllById(boardId);}

}
