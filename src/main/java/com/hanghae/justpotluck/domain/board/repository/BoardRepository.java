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

    Page<Board> findAllByOrderByViewCountDesc(Pageable pageable);

    Page<Board> findAllByOrderByCookTimeAsc(Pageable pageable);

    @Query(value = "select * from Board b join Ingredient i using (board_id) where i.ingredient in :include and i.ingredient not in :exclude and b.category = :category order by b.cook_time asc", nativeQuery = true)
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsOrderByCookTimeAsc(@Param("include") ArrayList<String> include, @Param("exclude") ArrayList<String> exclude, @Param("category")String category);
    @Query(value = "select * from Board b join Ingredient i using (board_id) where i.ingredient in :include and i.ingredient not in :exclude and b.category = :category order by b.view_count desc", nativeQuery = true)
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsOrderByViewCountDesc(@Param("include") ArrayList<String> include, @Param("exclude") ArrayList<String> exclude, @Param("category")String category);
    @Query(value = "select * from Board b join Ingredient i using (board_id) where i.ingredient in :include and i.ingredient not in :exclude and b.category = :category order by (size(:include)/size(b.ingredients)) desc", nativeQuery = true)
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsOrderByMatchDesc(@Param("include") ArrayList<String> include, @Param("exclude") ArrayList<String> exclude, @Param("category")String category);

    Page<Board> findAllByTitleContaining(String title, Pageable pageable);
    Page<Board> findByUserOrderByIdDesc(User user, Pageable pageable);

    Page<Board> findByUserAndIsBookmark(User user, Boolean isBookmark, Pageable pageable);

    @Modifying
    @Query("update Board b set b.viewCount = b.viewCount + 1 where b.id = :id")
    int updateView(Long id);

    List<Board> findAllByCategory(String category);
}
