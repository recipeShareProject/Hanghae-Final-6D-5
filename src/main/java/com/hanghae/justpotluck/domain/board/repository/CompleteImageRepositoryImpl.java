package com.hanghae.justpotluck.domain.board.repository;

import com.hanghae.justpotluck.domain.board.entity.CompleteImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hanghae.justpotluck.domain.board.entity.QBoard.board;
import static com.hanghae.justpotluck.domain.board.entity.QCompleteImage.completeImage;

@RequiredArgsConstructor
public class CompleteImageRepositoryImpl implements CompleteImageRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CompleteImage> findBySavedImageUrl(Long boardId) {
        return queryFactory
                .selectFrom(completeImage)
                .innerJoin(completeImage.board, board)
                .where(board.id.eq(boardId))
                .fetch();
    }
}
