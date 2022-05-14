package com.example.sparta.hanghaefinal.domain.dto.board.response.board;

import com.example.sparta.hanghaefinal.config.Timestamped;
import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.review.Review;
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
    private int view;
    private boolean bookmark;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.nickname = entity.getNickname();
        this.view = entity.getView();
        this.bookmark = entity.isBookmark();
        this.reviews = entity.getReviewList();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }
}
