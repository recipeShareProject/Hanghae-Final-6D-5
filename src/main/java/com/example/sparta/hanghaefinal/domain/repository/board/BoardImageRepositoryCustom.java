package com.example.sparta.hanghaefinal.domain.repository.board;

import com.example.sparta.hanghaefinal.domain.entity.board.Image;

import java.util.List;

public interface BoardImageRepositoryCustom {

    List<Image> findBySavedImageUrl(Long boardId);
}
