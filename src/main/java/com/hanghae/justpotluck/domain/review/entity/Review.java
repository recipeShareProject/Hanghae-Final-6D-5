package com.hanghae.justpotluck.domain.review.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.entity.Image;
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

//    private User user;
//    private String nickname;

    @JsonBackReference
    @OneToMany(
            mappedBy = "review",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Image> images = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Review(Board board, String comment) {
        this.board = board;
        this.comment = comment;
//        this.nickname = nickname;
    }

    public static Review createReview(String comment, Board board) {
        return Review.builder()
                .comment(comment)
                .board(board)
                .build();
    }

    public void updateReview(String comment) {
        this.comment = comment;
    }

    public void addImage(Image image) {
        this.images.add(image);
        if (image.getReview() != this) {
            image.setReview(this);
        }
    }


}
