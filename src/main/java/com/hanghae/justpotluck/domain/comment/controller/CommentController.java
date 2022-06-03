package com.hanghae.justpotluck.domain.comment.controller;

import com.hanghae.justpotluck.domain.comment.dto.request.CommentRequestDto;
import com.hanghae.justpotluck.domain.comment.dto.response.CommentResponseDto;
import com.hanghae.justpotluck.domain.comment.dto.response.CommentUpdateDto;
import com.hanghae.justpotluck.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comment/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto commentSave(@PathVariable("postId") Long postId, @RequestBody CommentRequestDto requestDto){
       return commentService.save(postId, requestDto);
//        return new ResponseEntity<>(new Success(true, "댓글 달기 성공"), HttpStatus.OK);
    }

    //"/api/comment/{commentId}/recomment"
    @PostMapping("/api/comment/{postId}/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto reCommentSave(@PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId,
                              @RequestBody CommentRequestDto requestDto){
        return commentService.saveReComment(postId, commentId, requestDto);
    }

    //"/api/comment/{commentId}/{recommentId}
    @PatchMapping("/api/comment/{postId}/{commentId}")
    public CommentResponseDto  commentModify(@PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId, @RequestBody CommentUpdateDto requestDto){
        return commentService.modify(postId, commentId, requestDto);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) throws Exception {
        commentService.remove(commentId);
    }
}
