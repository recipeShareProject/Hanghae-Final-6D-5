package com.hanghae.justpotluck.domain.ingredient.controller;

import com.hanghae.justpotluck.domain.ingredient.service.IngredientService;
import com.hanghae.justpotluck.domain.process.dto.request.ProcessSaveRequest;
import com.hanghae.justpotluck.domain.process.dto.response.ProcessResponseDto;
import com.hanghae.justpotluck.domain.process.service.RecipeProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class IngredientController {

    private final RecipeProcessService processService;
    private final IngredientService ingredientService;

    @PostMapping("/board/{boardId}/ingredient")
    public ProcessResponseDto saveProcess(@RequestBody ProcessSaveRequest requestDto, @PathVariable Long boardId) {
        return processService.saveProcess(requestDto, boardId);
    }

}
