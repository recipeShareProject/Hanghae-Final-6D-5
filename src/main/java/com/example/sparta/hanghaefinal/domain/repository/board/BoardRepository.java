package com.example.sparta.hanghaefinal.domain.repository.board;


import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>{
    Page<Board> OrderByIdDesc(Pageable pageable);

    Optional<Board> findById(Long id);
}
