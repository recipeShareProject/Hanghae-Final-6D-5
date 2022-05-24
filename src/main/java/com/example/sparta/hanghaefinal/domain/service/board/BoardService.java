package com.example.sparta.hanghaefinal.domain.service.board;

import com.example.sparta.hanghaefinal.advice.RestException;
import com.example.sparta.hanghaefinal.aws.S3Uploader;
import com.example.sparta.hanghaefinal.domain.dto.board.request.BoardRequestDto;
import com.example.sparta.hanghaefinal.domain.dto.board.request.BoardSearchDto;
import com.example.sparta.hanghaefinal.domain.dto.board.response.board.BoardResponseDto;
import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.board.Bookmark;
import com.example.sparta.hanghaefinal.domain.entity.board.Image;
import com.example.sparta.hanghaefinal.domain.entity.user.User;
import com.example.sparta.hanghaefinal.domain.repository.board.BoardRepository;
import com.example.sparta.hanghaefinal.domain.repository.board.ImageRepository;
import com.example.sparta.hanghaefinal.domain.repository.bookmark.BookmarkRepository;
import com.example.sparta.hanghaefinal.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BookmarkRepository bookmarkRepository;


    @Transactional
    public List<BoardResponseDto> findAll(int pagingCnt){
        Pageable pageRequest = PageRequest.of(pagingCnt, 10, Sort.by("createdAt").descending());
        Page<Board> boards = boardRepository.findAll(pageRequest);
        List<BoardResponseDto> responseDto = new ArrayList<>();
        for (Board board: boards){
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            responseDto.add(boardResponseDto);
        }
        return responseDto;
    }

//    추후에 수정 해야하는 쿼리 존재

    @Transactional
    public List<BoardResponseDto> findWantedRecipe(BoardSearchDto requestDto){
        String nation = requestDto.getNation();
        List<String> include = requestDto.getInclude();
        List<String> exclude = requestDto.getExclude();
        String search = requestDto.getSearch();

        List<Board> boards;

        if (requestDto.getOrder() == "view"){
            boards = boardRepository.findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByViewCountDesc(include, exclude, nation, search);
        }
        else{
            boards = boardRepository.findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByCookingTimeDesc(include, exclude, nation, search);}


        List<BoardResponseDto> responseDto = new ArrayList<>();

        for (Board board: boards){
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            responseDto.add(boardResponseDto);
        }
        return responseDto;
    }


    @Transactional
    public void saveRecipe(BoardRequestDto requestDto, String username){
        userRepository.findByName(username).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 username이 존재하지 않습니다.")
        );
        Board board = Board.builder()
                .cookingTime(requestDto.getCookingTime())
                .nation(requestDto.getNation())
                .quantity(requestDto.getQuantity())
                .title(requestDto.getTitle())
                .build();
        boardRepository.save(board);
    }



    @Transactional
    public void modifyRecipe(Long boardId, BoardRequestDto requestDto, String username) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );

        if (board.getWriter().getName().equals(username)) {
            board.update(requestDto);
        } else {
            throw new RestException(HttpStatus.BAD_REQUEST, "username이 일치하지 않습니다.");
        }
    }


    @Transactional
    public BoardResponseDto findOne(Long boardId) {
        Board board =  boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        return new BoardResponseDto(board);
    }

    public Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        return board;
    }

    @Transactional
    public void deleteRecipe(Long boardId) {boardRepository.deleteById(boardId);}

    @Transactional
    public boolean bookmarkBoard(Long boardId) {
        Board board = getBoard(boardId);

        Optional<Bookmark> bookmark = bookmarkRepository.findByBoard(board);

        if (bookmark.isEmpty()) {
            bookmarkRepository.save(Bookmark.createBookmark(board));
            return true;
        } else {
            bookmarkRepository.delete(bookmark.get());
            return false;
        }
    }



//    @Transactional
//    public BoardOneResponse getOneBoard(Long boardId) {
//        return boardRepository.findOneBoardById(boardId).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
//        );
//    }


}
