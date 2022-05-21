package com.example.sparta.hanghaefinal.domain.entity.board;

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
public class RecipeProcess {

    @Id
    private Long id;

    @ManyToOne
    private Board board;

    @Column
    private Integer processNumber;

    @Column
    private String process;

    @Column
    private String image;

    @Builder
    public RecipeProcess(Board board, Integer processNumber, String process, String image){
        this.board = board;
        this.processNumber = processNumber;
        this.process = process;
        this.image = image;
    }
}
