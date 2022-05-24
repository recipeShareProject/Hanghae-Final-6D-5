package com.example.sparta.hanghaefinal.domain.repository.board;


import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>{
    Page<Board> OrderByIdDesc(Pageable pageable);

    @Query(value = "select p from Board p where p.ingredients in :include and p.ingredients not in :exclude and p.nation = :nation and p.title like :title order by p.cookingTime")
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByCookingTimeDesc(@Param("include") List<String> include, @Param("exclude") List<String> exclude, @Param("nation")String category, @Param("title") String title);

    @Query(value = "select p from Board p where p.ingredients in :include and p.ingredients not in :exclude and p.nation = :nation and p.title like :title order by p.viewCount")
    List<Board> findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByViewCountDesc(List<String> include, List<String> exclude, String category, String word);

    Optional<Board> findById(Long id);
}
