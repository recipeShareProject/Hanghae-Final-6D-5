package com.hanghae.justpotluck.domain.board.controller;

import com.hanghae.justpotluck.domain.board.dto.request.BoardSaveRequestDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardSearchDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardUpdateRequestDto;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardListResponse;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardResponseDto;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardUpdateResponse;
import com.hanghae.justpotluck.domain.board.dto.response.bookmark.BookmarkResponse;
import com.hanghae.justpotluck.domain.board.service.BoardService;
import com.hanghae.justpotluck.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/board")
//    public BoardResponseDto saveBoard(
//            @RequestPart(value = "image", required = false) List<MultipartFile> images,
//            @RequestPart BoardSaveRequestDto requestDto) throws Exception {
//        return boardService.saveBoard(requestDto, images);
//    }
    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long boardId) {
        boardService.updateView(boardId);
        return ResponseEntity.ok(boardService.getBoard(boardId));
    }

    @GetMapping("/board")
    public List<BoardListResponse> getAllBoard() {
        return boardService.getAllBoard();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/board")
    public ResponseEntity<BoardResponseDto> saveBoard(@AuthenticationPrincipal UserPrincipal userPrincipal, @ModelAttribute BoardSaveRequestDto requestDto) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.saveBoard(requestDto));
    }

    @PostMapping("/api/community/search")
    public List<BoardResponseDto> findWantedRecipe(BoardSearchDto requestDto){
        return boardService.findWantedRecipe(requestDto);
    }

    @PatchMapping("/board/{boardId}")
    public ResponseEntity<BoardUpdateResponse> updateBoard(@PathVariable Long boardId,
                                                           @ModelAttribute BoardUpdateRequestDto requestDto) {
        return ResponseEntity.ok(boardService.updateBoard(boardId, requestDto));
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/board/{boardId}/bookmark")
    public ResponseEntity<BookmarkResponse> bookmarkBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(new BookmarkResponse(boardService.bookmarkBoard(boardId)));
    }

//    @GetMapping("/board/{boardId}")
//    public ResponseEntity<BoardOneResponse> getOnePost(@PathVariable Long boardId) {
//        return ResponseEntity.ok(boardService.getOneBoard(boardId));
//    }


//    //글, 파일 수정할 때
//    @PatchMapping("/board/{id}")
//    public BoardResponseDto updateBoard(@PathVariable Long id, BoardUploadImageRequest boardUploadImageRequest) throws Exception {
//
//        //제목, 내용 변경
//        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
//                .title(boardUploadImageRequest.getTitle())
//                .contents(boardUploadImageRequest.getContents())
//                .build();
//        return boardService.updateBoard(id, requestDto);
//    }
//
//    @PostMapping("/board/{id}/delete")
//    public void deleteBoard(@PathVariable Long id) {
//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 없습니다.")
//        );
//        boardService.deleteBoard(id);
//    }
//
//    @GetMapping("/board/{id}")
//    public BoardResponseDto findBoard(@PathVariable Long id) {
//        boardService.updateView(id);
//        return boardService.findBoard(id);
//    }
//
//    @PostMapping("/board/{boardId}/bookmark")
//    public void addBookmark(@PathVariable Long boardId) {
//        bookmarkService.saveBookmark(boardId);
//    }
//
//    @PostMapping("/board/{boardId}/bookmark/delete")
//    public void removeBookmark(@PathVariable Long boardId) {
//        bookmarkService.removeBookmark(boardId);
//    }
}
