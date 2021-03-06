package com.hanghae.justpotluck.domain.review.service;


import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.repository.BoardRepository;
import com.hanghae.justpotluck.domain.review.dto.request.ReviewSaveRequestDto;
import com.hanghae.justpotluck.domain.review.dto.request.ReviewUpdateRequestDto;
import com.hanghae.justpotluck.domain.review.dto.response.ReviewResponse;
import com.hanghae.justpotluck.domain.review.dto.response.ReviewSaveResponse;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.domain.review.entity.ReviewImage;
import com.hanghae.justpotluck.domain.review.repository.ReviewImageRepository;
import com.hanghae.justpotluck.domain.review.repository.ReviewRepository;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.aws.S3Uploader;
import com.hanghae.justpotluck.global.exception.RestException;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final BoardRepository boardRepository;
    private final UserUtil userUtil;
    private final S3Uploader s3Uploader;

    @Transactional
    public ReviewSaveResponse saveReview(ReviewSaveRequestDto requestDto) {
        User user = userUtil.findCurrentUser();
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        Review review = reviewRepository.save(Review.createReview(requestDto, board, user));
        List<String> reviewImages = uploadReviewImages(requestDto, review);
        return new ReviewSaveResponse(review, reviewImages);
    }

    private List<String> uploadReviewImages(ReviewSaveRequestDto requestDto, Review review) {
        return requestDto.getImages().stream()
                .map(image -> s3Uploader.upload(image, "review"))
                .map(reviewUrl -> createReviewImage(review, reviewUrl))
                .map(reviewImage -> reviewImage.getImageUrl())
                .collect(Collectors.toList());
    }

    private ReviewImage createReviewImage(Review review, String reviewUrl) {
        return reviewImageRepository.save(ReviewImage.builder()
                .imageUrl(reviewUrl)
                .storeFileName(StringUtils.getFilename(reviewUrl))
                .review(review)
                .build());
    }

    @Transactional
    public ReviewResponse updateReview(ReviewUpdateRequestDto requestDto) {
        User user = userUtil.findCurrentUser();
        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );
        validateDeletedImages(requestDto);
        uploadReviewImages(requestDto, review);
        List<String> saveImages = getSaveImages(requestDto);
        review.updateReview(requestDto);
        return new ReviewResponse(review, saveImages);
    }

    private void validateDeletedImages(ReviewUpdateRequestDto requestDto) {
        reviewImageRepository.findBySavedImageUrl(requestDto.getReviewId()).stream()
                .filter(reviewImage -> !requestDto.getSaveImageUrl().stream().anyMatch(Predicate.isEqual(reviewImage.getImageUrl())))
                .forEach(url -> {
                    reviewImageRepository.delete(url);
                    s3Uploader.deleteReviewImage(url.getImageUrl());
                });
    }
    private void uploadReviewImages(ReviewUpdateRequestDto request, Review review) {
        request.getImages()
                .stream()
                .forEach(file -> {
                    String url = s3Uploader.upload(file, "review");
                    createReviewImage(review, url);
                });
    }

    /**
     * PostImage 테이블에 저장 되어있는 이미지 경로를 추출
     */
    private List<String> getSaveImages(ReviewUpdateRequestDto request) {
        return reviewImageRepository.findBySavedImageUrl(request.getReviewId())
                .stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        User user = userUtil.findCurrentUser();
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );
        reviewRepository.delete(review);
    }

}
