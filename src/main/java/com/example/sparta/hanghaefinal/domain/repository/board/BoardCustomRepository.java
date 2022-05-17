package com.example.sparta.hanghaefinal.domain.repository.board;

import com.example.sparta.hanghaefinal.domain.dto.board.response.board.BoardOneResponse;

import java.util.Optional;

public interface BoardCustomRepository {
    Optional<BoardOneResponse> findOneBoardById(Long boardId);

//    Page<RecentBoardResponse> findRecentBoards(Pageable pageable);

}
