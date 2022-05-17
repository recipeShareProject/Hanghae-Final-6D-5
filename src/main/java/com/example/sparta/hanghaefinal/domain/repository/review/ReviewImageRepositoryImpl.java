package com.example.sparta.hanghaefinal.domain.repository.review;

import com.example.sparta.hanghaefinal.domain.entity.review.ReviewImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.sparta.hanghaefinal.domain.entity.review.QReview.review;
import static com.example.sparta.hanghaefinal.domain.entity.review.QReviewImage.reviewImage;


@RequiredArgsConstructor
public class ReviewImageRepositoryImpl implements ReviewImageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ReviewImage> findBySavedImageUrl(Long reviewId) {
        return queryFactory
                .selectFrom(reviewImage)
                .innerJoin(reviewImage.review, review)
                .where(review.id.eq(reviewId))
                .fetch();
    }
}
