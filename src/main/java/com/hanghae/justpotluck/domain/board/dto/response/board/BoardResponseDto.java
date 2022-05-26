package com.hanghae.justpotluck.domain.board.dto.response.board;

import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto extends Timestamped {
    private Long id;
    private String title;
    private List<String> process;
    private String category;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String cookTime;
    private List<String> processImageUrl;
    private List<String> completeImageUrl;
    private List<Review> reviews;
    private String quantity;
    private int viewCount;

    public BoardResponseDto(Board entity, List<String> processImages, List<String> completeImages) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.category = entity.getCategory();
        this.process = entity.getProcess();
        this.cookTime = entity.getCookTime();
//        this.user = entity.getUser();
        this.quantity = entity.getQuantity();
        this.viewCount = entity.getViewCount();
        this.processImageUrl = processImages;
        this.completeImageUrl = completeImages;
        this.reviews = entity.getReviewList();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getUser().getName();
        this.viewCount = board.getViewCount();
//        this.bookmark = entity.isBookmark();
        this.reviews = board.getReviewList();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
//        this.process = ;
    }
}
