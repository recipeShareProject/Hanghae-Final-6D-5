package com.hanghae.justpotluck.domain.review.dto.response;

import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewResponse extends Timestamped {
    private Board board;
    private Long id;
    private String comment;
    private String nickname;
    private String category;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReviewResponse(Review entity, List<String> images) {
        this.board = entity.getBoard();
        this.id = entity.getId();
        this.comment = entity.getComment();
        this.category = entity.getCategory();
        this.nickname = entity.getUser().getName();
        this.images = images;
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
