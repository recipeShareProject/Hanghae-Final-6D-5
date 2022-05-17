package com.example.sparta.hanghaefinal.domain.service.board;

import com.example.sparta.hanghaefinal.aws.S3Uploader;
import com.example.sparta.hanghaefinal.domain.dto.board.request.BoardSaveRequestDto;
import com.example.sparta.hanghaefinal.domain.dto.board.request.BoardUpdateRequestDto;
import com.example.sparta.hanghaefinal.domain.dto.board.response.board.BoardOneResponse;
import com.example.sparta.hanghaefinal.domain.dto.board.response.board.BoardSaveResponse;
import com.example.sparta.hanghaefinal.domain.dto.board.response.board.BoardUpdateResponse;
import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.board.Bookmark;
import com.example.sparta.hanghaefinal.domain.entity.board.Image;
import com.example.sparta.hanghaefinal.domain.repository.board.BoardImageRepository;
import com.example.sparta.hanghaefinal.domain.repository.board.BoardRepository;
import com.example.sparta.hanghaefinal.domain.repository.bookmark.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final BookmarkRepository bookmarkRepository;
    private final S3Uploader s3Uploader;

//    private final FileHandler fileHandler;


    @Transactional
    public BoardSaveResponse saveBoard(BoardSaveRequestDto requestDto) throws Exception {
        Board board = boardRepository.save(Board.createBoard(requestDto.getTitle(), requestDto.getContents(), requestDto.getNickname()));
        List<String> boardImages = uploadBoardImages(requestDto, board);

        return new BoardSaveResponse(board.getId(), boardImages);
    }

    private List<String> uploadBoardImages(BoardSaveRequestDto requestDto, Board board) {
        return requestDto.getImages().stream()
                .map(image -> s3Uploader.upload(image, "board"))
                .map(url -> saveBoardImage(board, url))
                .map(boardImage -> boardImage.getImageUrl())
                .collect(Collectors.toList());
    }

    private Image saveBoardImage(Board board, String url) {
        return boardImageRepository.save(Image.builder()
                .imageUrl(url)
                .storeFileName(StringUtils.getFilename(url))
                .board(board)
                .build());
    }

    @Transactional
    public BoardUpdateResponse updateBoard(Long boardId, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        validateDeletedImages(requestDto);
        uploadBoardImages(requestDto, board);
        List<String> saveImages = getSaveImages(requestDto);
        board.update(requestDto.getTitle(), requestDto.getContents());
        return new BoardUpdateResponse(board.getId(), saveImages);
    }

    private void validateDeletedImages(BoardUpdateRequestDto requestDto) {
        boardImageRepository.findBySavedImageUrl(requestDto.getBoardId()).stream()
                .filter(image -> !requestDto.getSaveImageUrl().stream().anyMatch(Predicate.isEqual(image.getImageUrl())))
                .forEach(url -> {
                    boardImageRepository.delete(url);
                    s3Uploader.deleteImage(url.getImageUrl());
                });
    }
    private void uploadBoardImages(BoardUpdateRequestDto requestDto, Board board) {
        requestDto.getImages()
                .stream()
                .forEach(file -> {
                    String url = s3Uploader.upload(file, "board");
                    saveBoardImage(board, url);
                });
    }
    private List<String> getSaveImages(BoardUpdateRequestDto requestDto) {
        return boardImageRepository.findBySavedImageUrl(requestDto.getBoardId())
                .stream()
                .map(image -> image.getImageUrl())
                .collect(Collectors.toList());
    }


    @Transactional
    public BoardOneResponse getOneBoard(Long boardId) {
        return boardRepository.findOneBoardById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
    }

    public Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        return board;
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        boardRepository.delete(board);
    }

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
