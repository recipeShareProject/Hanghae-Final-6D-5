package com.example.sparta.hanghaefinal.domain.dto.board.response.board;

import com.example.sparta.hanghaefinal.config.Timestamped;
import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.board.RecipeProcess;
import com.example.sparta.hanghaefinal.domain.entity.review.Review;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class BoardResponseDto extends Timestamped {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<Review> reviews;
    private List<RecipeProcess> process;
    private List<Ingredient> ingredient;
    private int viewCount;
//    private boolean bookmark;

//    재료, 과정을 어떻게 넣어야 할까?
    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.writer = board.getWriter().getName();
        this.viewCount = board.getViewCount();
//        this.bookmark = entity.isBookmark();
        this.reviews = board.getReviewList();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
//        this.process = ;
    }
}
