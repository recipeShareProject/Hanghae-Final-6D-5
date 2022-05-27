package com.hanghae.justpotluck.domain.board.repository;


import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository{
    Page<Board> OrderByIdDesc(Pageable pageable);

    List<Board> findAllByOrderByCreatedAtDesc();

    @Query(value = "select p from Board p where p.ingredients in :include and p.ingredients not in :exclude and p.category = :category and p.title like :title order by p.cookTime", nativeQuery = true)
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByCookTimeDesc(@Param("include") ArrayList<String> include, @Param("exclude") ArrayList<String> exclude, @Param("category")String category, @Param("title") String title);

    @Query(value = "select p from Board p where p.ingredients in :include and p.ingredients not in :exclude and p.category = :category and p.title like :title order by p.viewCount desc", nativeQuery = true)
    List<Board> findAllByIngredientsInAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByViewCountDesc(ArrayList<String> include, ArrayList<String> exclude, String category, String title);

    @Query(value = "select p from Board p where p.ingredients in :include and p.ingredients not in :exclude and p.category = :category and p.title like :title order by (size(:include)/size(p.ingredients)) desc", nativeQuery = true)
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByMatchDesc(ArrayList<String> include, ArrayList<String> exclude, String category, String title);

    Page<Board> findByUserOrderByIdDesc(User user, Pageable pageable);

    Page<Board> findByUserAndIsBookmark(User user, Boolean isBookmark, Pageable pageable);
    @Modifying
    @Query("update Board b set b.viewCount = b.viewCount + 1 where b.id = :id")
    int updateView(Long id);
}
