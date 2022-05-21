package com.hanghae.justpotluck.domain.board.repository;


import com.hanghae.justpotluck.domain.board.entity.Image;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.sparta.hanghaefinal.domain.entity.board.QBoard.board;
import static com.example.sparta.hanghaefinal.domain.entity.board.QImage.image1;


@RequiredArgsConstructor
public class BoardImageRepositoryImpl implements BoardImageRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Image> findBySavedImageUrl(Long boardId) {
        return queryFactory
                .selectFrom(image1)
                .innerJoin(image1.board, board)
                .where(board.id.eq(boardId))
                .fetch();
    }


}
