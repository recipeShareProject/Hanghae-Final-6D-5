package com.hanghae.justpotluck.domain.ingredient.service;

import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.repository.BoardRepository;
import com.hanghae.justpotluck.domain.ingredient.dto.request.IngredientSaveRequest;
import com.hanghae.justpotluck.domain.ingredient.dto.request.IngredientUpdateRequestDto;
import com.hanghae.justpotluck.domain.ingredient.dto.response.IngredientResponse;
import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import com.hanghae.justpotluck.domain.ingredient.repository.IngredientRepository;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class IngredientService {
    private final UserUtil userUtil;
    private final BoardRepository boardRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional
    public IngredientResponse saveIngredient(IngredientSaveRequest requestDto, Long boardId) {
        User user = userUtil.findCurrentUser();
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 레시피가 존재하지 않습니다.")
        );
        Ingredient ingredient = ingredientRepository.save(Ingredient.createIngredient(requestDto, board));

        return new IngredientResponse(ingredient);
    }

    @Transactional
    public IngredientResponse updateIngredient(Long ingredientId, IngredientUpdateRequestDto requestDto) {
        User user = userUtil.findCurrentUser();
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(
                () -> new IllegalArgumentException("해당 재료가 존재하지 않습니다.")
        );

        ingredient.update(requestDto, user);
        return new IngredientResponse(ingredient);
    }

}
