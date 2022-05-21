package com.example.sparta.hanghaefinal.web.controller.board;

import com.example.sparta.hanghaefinal.advice.RestException;
import com.example.sparta.hanghaefinal.domain.Success;
import com.example.sparta.hanghaefinal.domain.dto.board.request.BoardRequestDto;
import com.example.sparta.hanghaefinal.domain.dto.board.response.board.BoardResponseDto;
import com.example.sparta.hanghaefinal.domain.dto.community.PostThumbnailDto;
import com.example.sparta.hanghaefinal.domain.entity.board.Board;
import com.example.sparta.hanghaefinal.domain.entity.board.Ingredient;
import com.example.sparta.hanghaefinal.domain.entity.user.User;
import com.example.sparta.hanghaefinal.domain.repository.board.BoardRepository;
import com.example.sparta.hanghaefinal.domain.service.board.BoardService;
import com.example.sparta.hanghaefinal.domain.service.board.IngredientService;
import com.example.sparta.hanghaefinal.domain.service.board.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;
    private final IngredientService ingredientService;
    private final ProcessService processService;
    private final BoardRepository boardRepository;


    @GetMapping("/api/board")
    public List<BoardResponseDto> findAll(HttpServletRequest request, @AuthenticationPrincipal User user) {
        int pagingCnt;
        if (request.getHeader("PAGING_CNT") == null) {
            pagingCnt = 0;
        } else {
            pagingCnt = Integer.parseInt(request.getHeader("PAGING_CNT"));
        }

        return boardService.findAll(pagingCnt);

    }

    @GetMapping("/api/board/{boardId}")
    public  BoardResponseDto findRecipeOne(@PathVariable("boardId") Long boardId, @AuthenticationPrincipal User user){
        return boardService.findOne(boardId);
    }


    @PostMapping("api/board")
    public ResponseEntity<Success> saveRecipe(BoardRequestDto requestDto, @AuthenticationPrincipal User user){
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .quantity(requestDto.getQuantity())
                .nation(requestDto.getNation())
                .cookingTime(requestDto.getCookingTime())
                .build();

        ingredientService.saveRecipe(board, requestDto.getIngredient(), requestDto.getAmount());
        processService.saveRecipe(board, requestDto.getProcess(), requestDto.getImages());
        boardService.saveRecipe(board, user.getName());

        return new ResponseEntity<>(new Success(true, "게시글 추가 성공"), HttpStatus.OK);
    }

    @PatchMapping("api/community/{recipeId}")
    public ResponseEntity<Success> modifyRecipe(BoardRequestDto requestDto, @PathVariable("recipeId") Long recipeId, @AuthenticationPrincipal User user){
        Board board = boardRepository.findById(recipeId).orElseThrow(()
        -> new RestException(HttpStatus.NOT_FOUND, "해당 게시물이 존재하지 않습니다."));

        ingredientService.modifyRecipe(board, requestDto.getIngredient(), requestDto.getAmount());
        processService.modifyRecipe(board, requestDto.getProcess(), requestDto.getImages());
        boardService.modifyRecipe(recipeId, requestDto, user.getName());

        return new ResponseEntity<>(new Success(true, "게시글 수정 성공"), HttpStatus.OK);
    }

    @DeleteMapping("api/community/{recipeId}")
    public ResponseEntity<Success> deleteRecipe(BoardRequestDto requestDto, @PathVariable("recipeId") Long recipeId) {
        ingredientService.deleteRecipe(recipeId);
        processService.deleteRecipe(recipeId);
        boardService.deleteRecipe(recipeId);
        return new ResponseEntity<>(new Success(true, "게시글 삭제 성공"), HttpStatus.OK);
    }
}
