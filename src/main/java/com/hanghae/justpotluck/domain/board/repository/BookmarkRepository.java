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

//    @Modifying
//    @Query(value = "INSERT INTO bookmark(board_id, user_id) VALUES(:boardID, :userId)", nativeQuery = true)
//    void bookmark(Long boardId, Long userId);
//
//    @Modifying
//    @Query(value = "DELETE FROM bookmark WHERE board_id = :boardId AND user_id = :userId", nativeQuery = true)
//    void cancelbookmark(Long boardId, Long userId);

    Optional<Bookmark> findByUserAndBoard(User user, Board board);

    Page<Bookmark> findByUserOrderByIdDesc(User user, Pageable pageable);
//    @Modifying
//    @Query(value = "INSERT INTO bookmark(board_id) VALUES(:boardId)", nativeQuery = true)
//    void addBookmark(Long boardId);
//
//    @Modifying
//    @Query(value = "DELETE FROM bookmark where board_id = :boardId", nativeQuery = true)
//    void removeBookmark(Long boardId);
}
