package com.hanghae.justpotluck.domain.board.entity;

import com.hanghae.justpotluck.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "board_id")
    @ManyToOne
    private Board board;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Builder
    public Bookmark(Board board, User user) {
        this.user = user;
        this.board = board;
    }

    public static Bookmark createBookmark(Board board, User user) {
        return Bookmark.builder()
                .board(board)
                .user(user)
                .build();
    }
}
