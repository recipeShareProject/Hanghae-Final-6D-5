package com.hanghae.justpotluck.domain.board.repository;


import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.entity.Bookmark;
import com.hanghae.justpotluck.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    Optional<Bookmark> findByBoard(Board board);

    Optional<Bookmark> findByUserAndBoard(User user, Board board);

    Page<Bookmark> findByUserOrderByIdDesc(User user, Pageable pageable);

}
