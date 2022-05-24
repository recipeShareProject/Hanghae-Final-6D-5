package com.example.sparta.hanghaefinal.domain.repository;

import com.example.sparta.hanghaefinal.domain.entity.community.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Posts> findAllByIngredientNotLikeAndIngredientLike(List<String> exclude, List<String> include);

}
