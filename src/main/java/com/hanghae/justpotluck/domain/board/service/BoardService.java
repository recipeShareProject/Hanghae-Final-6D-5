package com.hanghae.justpotluck.domain.board.service;

import com.hanghae.justpotluck.domain.board.dto.request.BoardSaveRequestDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardSearchDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardUpdateRequestDto;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardListResponse;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardResponseDto;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardUpdateResponse;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.entity.Bookmark;
import com.hanghae.justpotluck.domain.board.entity.Image;
import com.hanghae.justpotluck.domain.board.repository.BoardImageRepository;
import com.hanghae.justpotluck.domain.board.repository.BoardRepository;
import com.hanghae.justpotluck.domain.board.repository.BookmarkRepository;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.domain.review.repository.ReviewRepository;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.global.aws.S3Uploader;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final S3Uploader s3Uploader;
    private final UserUtil userUtil;

//    private final FileHandler fileHandler;


    @Transactional
    public BoardResponseDto saveBoard(BoardSaveRequestDto requestDto) throws Exception {
        User user = userUtil.findCurrentUser();
//        User user = userPrincipal.getUser();
        Board board = boardRepository.save(Board.createBoard(requestDto, user));
        List<String> boardImages = uploadBoardImages(requestDto, board);
//        List<String> boardImages2 = uploadBoardImages2(requestDto, board);

        return new BoardResponseDto(board, boardImages);
    }

    //    private List<String> uploadProcessList(BoardSaveRequestDto requestDto, Board board) {
//        return requestDto.getProcessList().stream()
//                .map(recipeProcess -> recipeProcess.toString())
//                .collect(Collectors.toList());
//    }

    private List<String> uploadBoardImages(BoardSaveRequestDto requestDto, Board board) {
        return requestDto.getProcessImages().stream()
                .map(image -> s3Uploader.upload(image, "board"))
                .map(url -> saveBoardImage(board, url))
                .map(image -> image.getImageUrl())
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
        User user = userUtil.findCurrentUser();
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        validateDeletedImages(requestDto);
        uploadBoardImages(requestDto, board);
        List<String> saveImages = getSaveImages(requestDto);
        board.update(requestDto, user);
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
        requestDto.getProcessImages()
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
    public Page<BoardListResponse> getAllBoard(Pageable pageable) {
        List<BoardListResponse> listBoard = new ArrayList<>();
        Page<Board> boards = boardRepository.findAllByOrderByViewCountDesc(pageable);
        Page<Board> boards2 = boardRepository.findAllByOrderByCookTimeAsc(pageable);

        for (Board board : boards) {
            List<String> boardImages = boardImageRepository.findBySavedImageUrl(board.getId())
                    .stream()
                    .map(image ->image.getImageUrl())
                    .collect(Collectors.toList());
            listBoard.add(new BoardListResponse(board, boardImages));
        }
        return new PageImpl<>(listBoard, pageable, boards.getTotalElements());
    }


    @Transactional
    public Board getOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        return board;
    }

    @Transactional
    public BoardResponseDto getBoard(Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
        );
        List<Review> reviews = reviewRepository.findAllByBoardIdOrderByIdDesc(boardId);
        List<String> boardImages = board.getProcessImages()
                .stream()
                .map(image ->image.getImageUrl())
                .collect(Collectors.toList());


        return new BoardResponseDto(board, boardImages);
    }

//    원하는 것 검색하는 기능
    @Transactional
    public List<BoardResponseDto> findWantedRecipe(BoardSearchDto requestDto){
        String category = requestDto.getCategory();
        ArrayList<String> include = requestDto.getInclude();
        ArrayList<String> exclude = requestDto.getExclude();
        String search = requestDto.getSearch();

        List<Board> boards;

        if (requestDto.getOrder() == "view"){
            boards = boardRepository.findAllByIngredientsInAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByViewCountDesc(include, exclude, category, search);
        }
        else if(requestDto.getOrder() == "cookTime"){
            boards = boardRepository.findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByCookTimeDesc(include, exclude, category, search);}
        else{
            boards = boardRepository.findAllByIngredientsContainingAndIngredientsNotLikeAndCategoryIsAndTitleContainsOrderByMatchDesc(include, exclude, category, search);}

        List<BoardResponseDto> responseDto = new ArrayList<>();

        for (Board board: boards){
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            responseDto.add(boardResponseDto);
        }
        return responseDto;
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        User user = userUtil.findCurrentUser();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        boardRepository.delete(board);
    }

    @Transactional
    public boolean bookmarkBoard(Long boardId) {
        User user = userUtil.findCurrentUser();
        Board board = getOneBoard(boardId);

        Optional<Bookmark> bookmark = bookmarkRepository.findByUserAndBoard(user, board);
        if (bookmark.isEmpty()) {
            bookmarkRepository.save(Bookmark.createBookmark(board, user));
            return true;
        }
        bookmarkRepository.delete(bookmark.get());
        return false;
    }


//    @Transactional
//    public boolean bookmarkBoard(Long boardId) {
//        Board board = getOneBoard(boardId);
//        User user = userUtil.findCurrentUser();
//        Bookmark bookmark = bookmarkRepository.findByBoard(board).orElseThrow(
//                () -> new IllegalArgumentException("해당 북마크가 없습니다.")
//        );
//        user.getBookmarkList().add(bookmark);
//        if (bookmark == null) {
//            bookmarkRepository.save(Bookmark.createBookmark(board, user));
//            return true;
//        } else {
//            bookmarkRepository.delete(bookmark);
//            return false;
//        }
//    }

    /* Views Counting */
    @Transactional
    public int updateView(Long id) {
        return boardRepository.updateView(id);
    }



//    @Transactional
//    public BoardOneResponse getOneBoard(Long boardId) {
//        return boardRepository.findOneBoardById(boardId).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
//        );
//    }


}
