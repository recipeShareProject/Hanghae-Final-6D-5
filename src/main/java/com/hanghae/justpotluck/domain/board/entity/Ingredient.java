package com.hanghae.justpotluck.domain.board.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class Ingredient {

    @Id
    private Long id;

    @ManyToOne
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
}