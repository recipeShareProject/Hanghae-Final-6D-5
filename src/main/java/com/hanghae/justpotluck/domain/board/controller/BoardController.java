package com.hanghae.justpotluck.domain.board.controller;

import com.hanghae.justpotluck.domain.board.dto.request.BoardSaveRequestDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardSearchByTitleDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardSearchDto;
import com.hanghae.justpotluck.domain.board.dto.request.BoardUpdateRequestDto;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardListResponse;
import com.hanghae.justpotluck.domain.board.dto.response.board.BoardResponseDto;
import com.hanghae.justpotluck.domain.board.dto.response.bookmark.BookmarkResponse;
import com.hanghae.justpotluck.domain.board.service.BoardService;
import com.hanghae.justpotluck.domain.process.service.RecipeProcessService;
import com.hanghae.justpotluck.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final RecipeProcessService processService;

    @GetMapping("/board")
    public Page<BoardListResponse> getAllBoard(Pageable pageable) {
        return boardService.getAllBoard(pageable);
    }

    @GetMapping("/board/time")
    public Page<BoardListResponse> getAllBoardByCookTime(Pageable pageable) {
        return boardService.getAllBoardByCookTime(pageable);
    }

    @PostMapping("/board/search/title")
    public Page<BoardListResponse> getAllBoardByTitle(Pageable pageable, @RequestBody BoardSearchByTitleDto requestDto) {
        return boardService.getAllBoardByTitle(requestDto, pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/board")
    public ResponseEntity<BoardResponseDto> saveBoard(@AuthenticationPrincipal UserPrincipal userPrincipal, @ModelAttribute BoardSaveRequestDto requestDto) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.saveBoard(requestDto));
    }

    @PostMapping("/board/search")
    public List<BoardResponseDto> findWantedRecipe(@RequestBody BoardSearchDto requestDto) {

        return boardService.findWantedRecipe(requestDto);
    }

    @PatchMapping("/board/{boardId}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long boardId,
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

}
