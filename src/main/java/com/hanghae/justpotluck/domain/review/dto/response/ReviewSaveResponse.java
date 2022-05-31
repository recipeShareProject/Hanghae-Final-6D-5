package com.hanghae.justpotluck.domain.review.dto.response;

import com.hanghae.justpotluck.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSaveResponse {
    private Long boardId;
    private Long reviewId;
    private String comment;
    private String category;
    private List<String> images = new ArrayList<>();

    public ReviewSaveResponse(Review review, List<String> images) {
        this.boardId = review.getBoard().getId();
        this.reviewId = review.getId();
        this.images = images;
        this.category = review.getCategory();
        this.comment = review.getComment();
    }
}
