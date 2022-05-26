package com.hanghae.justpotluck.domain.board.repository;


import com.hanghae.justpotluck.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository{
    Page<Board> OrderByIdDesc(Pageable pageable);

    @Query(value = "select p from Board p where p.ingredients in :include and p.ingredients not in :exclude and p.category = :nation and p.title like :title order by p.cookTime")
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByCookingTimeDesc(@Param("include") List<String> include, @Param("exclude") List<String> exclude, @Param("nation")String category, @Param("title") String title);

    @Query(value = "select p from Board p where p.ingredients in :include and p.ingredients not in :exclude and p.category = :nation and p.title like :title order by p.viewCount desc")
    List<Board> findAllByIngredientsInAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByViewCountDesc(List<String> include, List<String> exclude, String category, String word);

    @Query(value = "select p from Board p where p.ingredients in :include and p.ingredients not in :exclude and p.category = :nation and p.title like :title order by (size(:include)/size(p.ingredients)) desc")
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByMatchDesc(List<String> include, List<String> exclude, String category, String word);
}
