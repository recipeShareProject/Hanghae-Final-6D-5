package com.hanghae.justpotluck.domain.review.repository;

import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
    Page<Review> findByBoardOrderByIdDesc(Board board, Pageable pageable);

    List<Review> findAllByBoardIdOrderByIdDesc(Long boardId);
    Page<Review> findByUserOrderByIdDesc(User user, Pageable pageable);
}
