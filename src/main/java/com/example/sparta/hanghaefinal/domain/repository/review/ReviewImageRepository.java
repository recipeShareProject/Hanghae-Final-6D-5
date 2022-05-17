package com.example.sparta.hanghaefinal.domain.repository.review;


import com.example.sparta.hanghaefinal.domain.entity.review.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long>, ReviewImageRepositoryCustom {
}
