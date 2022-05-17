package com.example.sparta.hanghaefinal.controller;


import com.example.sparta.hanghaefinal.domain.Success;
import com.example.sparta.hanghaefinal.dto.CommentRequestDto;
import com.example.sparta.hanghaefinal.dto.CommentUpdateDto;
import com.example.sparta.hanghaefinal.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Success> commentSave(@PathVariable("postId") Long postId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal Users user){
        commentService.save(postId, requestDto);
        return new ResponseEntity<>(new Success(true, "댓글 달기 성공"), HttpStatus.OK);
    }

    @PostMapping("/api/comment/{postId}/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Success> reCommentSave(@PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId,
                              @RequestBody CommentRequestDto requestDto){
        commentService.saveReComment(postId, commentId, requestDto);
        return new ResponseEntity<>(new Success(true, "답글 달기 성공"), HttpStatus.OK);
    }

    @PatchMapping("/api/comment/{postId}/{commentId}")
    public ResponseEntity<Success>  commentModify(@PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId, @RequestBody CommentUpdateDto requestDto){
        commentService.modify(postId, commentId, requestDto);
        return new ResponseEntity<>(new Success(true, "댓글 수정 완료"), HttpStatus.OK);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) throws Exception {
        commentService.remove(commentId);
    }
}
