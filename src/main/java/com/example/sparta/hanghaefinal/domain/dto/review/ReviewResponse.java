package com.example.sparta.hanghaefinal.domain.dto.review;

import com.example.sparta.hanghaefinal.config.Timestamped;
import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.review.Review;
import lombok.Data;

import java.time.LocalDateTime;

//@Data
public class ReviewResponse extends Timestamped {
    private Board board;
    private Long id;
    private String contents;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReviewResponse(Review entity) {
        this.board = entity.getBoard();
        this.id = entity.getId();
        this.contents = entity.getContents();
        this.nickname = entity.getNickname();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
