package com.example.sparta.hanghaefinal.domain.entity.board;

import com.example.sparta.hanghaefinal.config.Timestamped;
import com.example.sparta.hanghaefinal.domain.entity.review.Review;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String contents;
    private String nickname;
    private int view = 0;
    private boolean bookmark;

    @JsonBackReference
    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Image> images = new ArrayList<>();

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder
    public Board(String title, String contents, String nickname,
                 List<Review> reviewList, int view, boolean bookmark) {
        this.title = title;
        this.contents = contents;
        this.nickname = nickname;
        this.reviewList = reviewList;
        this.view = view;
        this.bookmark = isBookmark();
    }

    public static Board createBoard(String title, String contents, String nickname) {
        return Board.builder()
                .title(title)
                .contents(contents)
                .nickname(nickname)
                .build();
    }


    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void addImage(Image image) {
        this.images.add(image);
        if (image.getBoard() != this) {
            image.setBoard(this);
        }
    }

}
