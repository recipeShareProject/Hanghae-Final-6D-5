package com.example.sparta.hanghaefinal.domain.repository.board;


import com.example.sparta.hanghaefinal.domain.entity.board.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImageRepository extends JpaRepository<Image, Long>, BoardImageRepositoryCustom {

    //게시글 전체 이미지 가져오기
//    List<Image> findAllByBoardId(Long boardId);
//
//    List<Image> deleteAllByBoardId(Long boardId);

}
