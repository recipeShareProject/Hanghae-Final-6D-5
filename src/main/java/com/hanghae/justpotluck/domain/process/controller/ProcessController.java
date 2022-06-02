package com.hanghae.justpotluck.domain.process.controller;

import com.hanghae.justpotluck.domain.process.dto.ProcessResponseDto;
import com.hanghae.justpotluck.domain.process.dto.ProcessSaveRequest;
import com.hanghae.justpotluck.domain.process.service.RecipeProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ProcessController {

    private final RecipeProcessService processService;

    @PostMapping("/board/{boardId}/process")
    public ProcessResponseDto saveProcess(@ModelAttribute ProcessSaveRequest requestDto, @PathVariable Long boardId) {
        return processService.saveProcess(requestDto, boardId);
    }

}
