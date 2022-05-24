package com.example.sparta.hanghaefinal.domain.entity.board;

import com.example.sparta.hanghaefinal.config.Timestamped;
import com.example.sparta.hanghaefinal.domain.dto.board.request.BoardRequestDto;
import com.example.sparta.hanghaefinal.domain.entity.review.Review;
import com.example.sparta.hanghaefinal.domain.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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

    private String quantity;

    private String nation;

    private String cookingTime;

//    이렇게 들어가도 되는지 모르겠네;;
    private List<String> ingredients = new ArrayList<>();

    private List<String> amount = new ArrayList<>();

    @ManyToOne
    private User writer;

    private int viewCount = 0;
    private boolean bookmark = false;

//    @JsonBackReference
//    @OneToMany(
//            mappedBy = "board",
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//            orphanRemoval = true
//    )
//    private List<Image> images = new ArrayList<>();

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

//    이거 왜 있는지 모르겠음...;;
    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder
    public Board(String title, String quantity, String nation, String cookingTime, List<String> ingredients, List<String> amount,
                 List<Review> reviewList, int viewCount) {
        this.title = title;
        this.quantity = quantity;
        this.nation = nation;
        this.ingredients = ingredients;
        this.amount = amount;
        this.cookingTime = cookingTime;
        this.reviewList = reviewList;
        this.viewCount = viewCount;
    }

    public static Board createBoard(String title, String quantity, String nation, String cookingTime) {
        return Board.builder()
                .title(title)
                .quantity(quantity)
                .nation(nation)
                .cookingTime(cookingTime)
                .build();
    }

//    뭘 업데이트하는지 생각 좀;;
    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.quantity = requestDto.getQuantity();
        this.nation = requestDto.getNation();
        this.cookingTime = requestDto.getCookingTime();
    }


//    public void addImage(Image image) {
//        this.images.add(image);
//        if (image.getBoard() != this) {
//            image.setBoard(this);
//        }
//    }

}
