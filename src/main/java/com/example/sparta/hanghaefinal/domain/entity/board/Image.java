package com.example.sparta.hanghaefinal.domain.entity.board;

import com.example.sparta.hanghaefinal.config.Timestamped;
import com.example.sparta.hanghaefinal.domain.entity.review.Review;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Image extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;


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

    public Image(Board board, String image) {
        this.board = board;
        this.image = image;
    }

    private String image;

    public void setBoard(Board board) {
        this.board = board;
        if (!board.getImages().contains(this)) {
            board.getImages().add(this);
        }
    }

    public void setReview(Review review) {
        this.review = review;
        if (!review.getImages().contains(this)) {
            review.getImages().add(this);
        }
    }

}
