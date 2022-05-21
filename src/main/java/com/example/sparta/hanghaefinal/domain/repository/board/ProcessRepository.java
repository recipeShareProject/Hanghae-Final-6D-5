package com.example.sparta.hanghaefinal.domain.repository.board;

import com.example.sparta.hanghaefinal.domain.entity.board.RecipeProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<RecipeProcess, Long> {
    void deleteAllById(Long boardId);
}
