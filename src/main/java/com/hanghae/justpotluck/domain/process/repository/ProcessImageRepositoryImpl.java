package com.hanghae.justpotluck.domain.process.repository;

import com.hanghae.justpotluck.domain.process.entity.ProcessImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hanghae.justpotluck.domain.process.entity.QProcessImage.processImage;
import static com.hanghae.justpotluck.domain.process.entity.QRecipeProcess.recipeProcess;

@RequiredArgsConstructor
public class ProcessImageRepositoryImpl implements ProcessImageRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProcessImage> findBySavedImageUrl(Long processId) {
        return queryFactory
                .selectFrom(processImage)
                .innerJoin(processImage.process, recipeProcess)
                .where(recipeProcess.id.eq(processId))
                .fetch();
    }
}
