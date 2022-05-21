package com.hanghae.justpotluck.domain.board.dto.response.board;

import com.hanghae.justpotluck.global.config.Timestamped;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.review.entity.Review;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
public class BoardResponseDto extends Timestamped {
    private Long id;
    private String title;
    private String contents;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private ArrayList<String> imagePath;
    private List<Review> reviews;
    private int viewCount;
    private boolean bookmark;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.nickname = entity.getNickname();
        this.viewCount = entity.getViewCount();
        this.bookmark = entity.isBookmark();
        this.reviews = entity.getReviewList();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
