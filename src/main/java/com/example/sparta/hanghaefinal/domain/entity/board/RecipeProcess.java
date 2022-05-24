package com.example.sparta.hanghaefinal.domain.entity.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @OneToOne
    private Image image;

    @Builder
    public RecipeProcess(Board board, Integer processNumber, String process, Image image){
        this.board = board;
        this.processNumber = processNumber;
        this.process = process;
        this.image = image;
    }
}
