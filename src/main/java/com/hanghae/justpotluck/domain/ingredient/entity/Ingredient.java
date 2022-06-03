package com.hanghae.justpotluck.domain.ingredient.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.ingredient.dto.request.IngredientSaveRequest;
import com.hanghae.justpotluck.domain.ingredient.dto.request.IngredientUpdateRequestDto;
import com.hanghae.justpotluck.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column
    private String ingredient;

    private String include;
    private String exclude;

    @Column
    private String amount;

    @Builder
    public Ingredient(String ingredient, String amount, Board board){
        this.board = board;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public static Ingredient createIngredient(IngredientSaveRequest requestDto, Board board) {
        return Ingredient.builder()
                .ingredient(requestDto.getIngredient())
                .amount(requestDto.getAmount())
                .board(board)
                .build();
    }

    public void update(IngredientUpdateRequestDto requestDto, User user) {
        this.ingredient = requestDto.getIngredient();
        this.amount = requestDto.getAmount();
    }

    public void setBoard(Board board) {
        this.board = board;
        if (!board.getIngredients().contains(this)) {
            board.getIngredients().add(this);
        }
    }
}