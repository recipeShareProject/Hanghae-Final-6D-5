package com.example.sparta.hanghaefinal.domain.repository.review;


import com.example.sparta.hanghaefinal.domain.entity.review.ReviewImage;

import java.util.List;

public interface ReviewImageRepositoryCustom {
    List<ReviewImage> findBySavedImageUrl(Long reviewId);
}
