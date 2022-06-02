package com.hanghae.justpotluck.domain.process.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.process.dto.ProcessSaveRequest;
import com.hanghae.justpotluck.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class RecipeProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_process_id")
    private Long processId;


    private String process;

    @OneToOne
    @JoinColumn(name = "process_image_id")
    private List<ProcessImage> processImages;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public RecipeProcess(Long processId, String process, Board board, User user) {
        this.processId = processId;
        this.process = process;
        this.board = board;
        this.user = user;
    }

    public static RecipeProcess createProcess(ProcessSaveRequest requestDto, Board board, User user) {
        return RecipeProcess.builder()
                .process(requestDto.getProcess())
                .user(user)
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
