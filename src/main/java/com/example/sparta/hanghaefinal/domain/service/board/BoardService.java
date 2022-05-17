package com.example.sparta.hanghaefinal.domain.service.board;

import com.example.sparta.hanghaefinal.advice.RestException;
import com.example.sparta.hanghaefinal.aws.S3Uploader;
import com.example.sparta.hanghaefinal.domain.dto.board.request.BoardRequestDto;
import com.example.sparta.hanghaefinal.domain.dto.board.response.board.BoardResponseDto;
import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.user.User;
import com.example.sparta.hanghaefinal.domain.repository.board.BoardRepository;
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


import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BookmarkRepository bookmarkRepository;
    private final S3Uploader s3Uploader;

//    private final FileHandler fileHandler;

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


    @Transactional
    public void saveRecipe(Board board, String username){

        User result = userRepository.findByName(username).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 username이 존재하지 않습니다.")
        );
        boardRepository.save(board);
//        List<String> boardImages = uploadBoardImages(requestDto, board);
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
//    private void validateDeletedImages(BoardUpdateRequestDto requestDto) {
//        boardImageRepository.findBySavedImageUrl(requestDto.getBoardId()).stream()
//                .filter(image -> !requestDto.getSaveImageUrl().stream().anyMatch(Predicate.isEqual(image.getImageUrl())))
//                .forEach(url -> {
//                    boardImageRepository.delete(url);
//                    s3Uploader.deleteImage(url.getImageUrl());
//                });
//    }
//    private void uploadBoardImages(BoardUpdateRequestDto requestDto, Board board) {
//        requestDto.getImages()
//                .stream()
//                .forEach(file -> {
//                    String url = s3Uploader.upload(file, "board");
//                    saveBoardImage(board, url);
//                });
//    }
//    private List<String> getSaveImages(BoardUpdateRequestDto requestDto) {
//        return boardImageRepository.findBySavedImageUrl(requestDto.getBoardId())
//                .stream()
//                .map(image -> image.getImageUrl())
//                .collect(Collectors.toList());
//    }


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

//    @Transactional
//    public boolean bookmarkBoard(Long boardId) {
//        Board board = getBoard(boardId);
//
//        Optional<Bookmark> bookmark = bookmarkRepository.findByBoard(board);
//
//        if (bookmark.isEmpty()) {
//            bookmarkRepository.save(Bookmark.createBookmark(board));
//            return true;
//        } else {
//            bookmarkRepository.delete(bookmark.get());
//            return false;
//        }
//    }



//    @Transactional
//    public BoardOneResponse getOneBoard(Long boardId) {
//        return boardRepository.findOneBoardById(boardId).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
//        );
//    }


}
