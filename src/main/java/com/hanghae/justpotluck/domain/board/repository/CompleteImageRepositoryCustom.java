package com.hanghae.justpotluck.domain.board.repository;

import com.hanghae.justpotluck.domain.board.entity.CompleteImage;

import java.util.List;

public interface CompleteImageRepositoryCustom {

    List<CompleteImage> findBySavedImageUrl(Long boardId);
}
