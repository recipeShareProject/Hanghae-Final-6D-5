package com.hanghae.justpotluck.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hanghae.justpotluck.domain.board.dto.request.BoardSaveRequestDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardUpdateRequestDto;
import com.hanghae.justpotluck.domain.ingredient.entity.Ingredient;
import com.hanghae.justpotluck.domain.process.entity.RecipeProcess;
import com.hanghae.justpotluck.domain.review.entity.Review;
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
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
//    요리 이름
    private String title;
//    조리법
//    private String contents;
    private String quantity;
    private String category;
//    private String nickname;
    private int viewCount;
    private Boolean isBookmark;
    private String cookTime;

    //얘를 어떻게 할 건지
//    private ArrayList<String> ingredients;

    @JsonBackReference
    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Ingredient> ingredients;

    @JsonBackReference
    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Ingredient> includes;


    @JsonBackReference
    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Ingredient> excludes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @JsonBackReference
//    @OneToMany(
//            mappedBy = "board",
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//            orphanRemoval = true
//    )
//    private List<Image> processImages = new ArrayList<>();

    @JsonBackReference
    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Image> completeImages = new ArrayList<>();

    @JsonBackReference
    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<RecipeProcess> processes;
//    private ArrayList<String> process;
//    private List<Ingredient> ingredientList;

    @JsonIgnoreProperties({"board"})
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviewList;

    //이제 리스트로 받을 필요 없음
    //나중에 수정
//    @JsonIgnoreProperties({"board"})
//    @OneToMany(mappedBy = "board", orphanRemoval = true)
//    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Builder
    public Board(String title, User user,
                 List<Review> reviewList, String cookTime, String quantity,
                 List<Ingredient> ingredients,
                 List<RecipeProcess> processes, int viewCount, String category) {
        this.title = title;
        this.cookTime = cookTime;
        this.user = user;
        this.reviewList = reviewList;
        this.processes = processes;
        this.quantity = quantity;
        this.ingredients = ingredients;
        this.category = category;
        this.viewCount = viewCount;
//        this.bookmarkList = bookmarkList;
    }

    public static Board createBoard(BoardSaveRequestDto requestDto, User user) {
        return Board.builder()
                .title(requestDto.getTitle())
                .processes(requestDto.getProcesses())
                .category(requestDto.getCategory())
                .quantity(requestDto.getQuantity())
                .ingredients(requestDto.getIngredients())
                .user(user)
                .cookTime(requestDto.getCookTime())
                .build();
    }

    public void update(BoardUpdateRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.quantity = requestDto.getQuantity();
        this.category = requestDto.getCategory();
        this.cookTime = requestDto.getCookTime();
//        this.ingredients = requestDto.getIngredients();
//        this.processes = requestDto.getProcesses();
    }

    public void addImage(Image image) {
        this.completeImages.add(image);
        if (image.getBoard() != this) {
            image.setBoard(this);
        }
    }

}
