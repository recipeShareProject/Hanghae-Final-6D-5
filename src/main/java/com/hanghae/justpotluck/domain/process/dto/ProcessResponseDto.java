package com.hanghae.justpotluck.domain.process.dto;

import com.hanghae.justpotluck.domain.process.entity.RecipeProcess;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessResponseDto {
    private Long id;
    private Long boardId;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
    private String process;
    private List<String> processImages;

    public ProcessResponseDto(RecipeProcess process, List<String> processImages) {
        this.id = process.getProcessId();
        this.boardId = process.getBoard().getId();
        this.process = process.getProcess();
        this.processImages = processImages;
    }
}
