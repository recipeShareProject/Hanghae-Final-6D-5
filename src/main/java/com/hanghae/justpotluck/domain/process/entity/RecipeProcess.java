package com.hanghae.justpotluck.domain.process.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.process.dto.ProcessSaveRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "process")
@Entity
public class RecipeProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private Long id;


    private String process;

    @JsonBackReference
    @OneToMany(
            mappedBy = "process",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<ProcessImage> processImages = new ArrayList<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

//    @JsonBackReference
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;


    @Builder
    public RecipeProcess(Long id, String process, Board board) {
        this.id = id;
        this.process = process;
        this.board = board;
    }

    public static RecipeProcess createProcess(ProcessSaveRequest requestDto, Board board) {
        return RecipeProcess.builder()
                .process(requestDto.getProcess())
                .board(board)
                .build();
    }

    public void setBoard(Board board) {
        this.board = board;
        if (!board.getProcesses().contains(this)) {
            board.getProcesses().add(this);
        }
    }


}
