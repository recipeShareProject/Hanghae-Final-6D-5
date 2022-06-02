package com.hanghae.justpotluck.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column
    private String amount;

    @Builder
    public Ingredient(String ingredient, String amount, Board board){
        this.board = board;
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public void setBoard(Board board) {
        this.board = board;
        if (!board.getIngredients().contains(this)) {
            board.getIngredients().add(this);
        }
    }
}