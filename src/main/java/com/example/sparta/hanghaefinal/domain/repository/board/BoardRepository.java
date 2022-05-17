package com.example.sparta.hanghaefinal.domain.repository.board;


import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository{
    Page<Board> OrderByIdDesc(Pageable pageable);
}
