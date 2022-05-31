package com.hanghae.justpotluck.domain.review.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.review.dto.request.ReviewSaveRequestDto;
import com.hanghae.justpotluck.domain.review.dto.request.ReviewUpdateRequestDto;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String comment;
    private String category;
    private String nickname;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @JsonIgnore
    @OneToMany(
            mappedBy = "review",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<ReviewImage> images = new ArrayList<>();

    @Builder
    public Review(Board board, String comment, String nickname, String category, User user) {
        this.board = board;
        this.comment = comment;
        this.user = user;
        this.nickname = user.getName();
        this.category = category;
//        this.nickname = nickname;
    }

    public static Review createReview(ReviewSaveRequestDto requestDto, Board board, User user) {
        return Review.builder()
                .comment(requestDto.getComment())
                .board(board)
                .category(requestDto.getCategory())
                .user(user)
                .nickname(user.getName())
                .build();
    }

    public void updateReview(ReviewUpdateRequestDto requestDto) {
        this.comment = requestDto.getComment();
        this.category = requestDto.getCategory();
    }

//    public void setImage(ReviewImage image) {
//        this.images.add(image);
//        if (image.getReview() != this) {
//            image.setReview(this);
//        }
//    }
//
//    public void setBoard(Board board) {
//        this.board = board;
//        if (!board.getReviewList().contains(this)) {
//            board.getReviewList().add(this);
//        }

}
