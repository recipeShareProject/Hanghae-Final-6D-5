package com.hanghae.justpotluck.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "complete_image")
@Entity
public class Image extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complete_image_id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private String imageUrl;
    private String storeFileName;

//    private String origFileName;
//    private String filePath;
//    private Long fileSize;

    //    @Builder
//    public Image(String origFileName, String filePath, Long fileSize) {
//        this.origFileName = origFileName;
//        this.filePath = filePath;
//        this.fileSize = fileSize;
//    }

    @Builder
    public Image(String imageUrl, String storeFileName, Board board) {
        this.imageUrl = imageUrl;
        this.storeFileName = storeFileName;
        this.board = board;
    }

    public Image(Board board, String imageUrl) {
        this.board = board;
        this.imageUrl = imageUrl;
    }

//    private String image;

    public void setBoard(Board board) {
        this.board = board;
        if (!board.getCompleteImages().contains(this)) {
            board.getCompleteImages().add(this);
        }
    }
//    public void setReview(Review review) {
//        this.review = review;
//        if (!review.getImages().contains(this)) {
//            review.getImages().add(this);
//        }
//    }


}
