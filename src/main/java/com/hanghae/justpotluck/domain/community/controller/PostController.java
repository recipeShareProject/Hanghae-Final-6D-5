package com.hanghae.justpotluck.domain.community.controller;

import com.hanghae.justpotluck.domain.community.dto.request.PostRequestDto;
import com.hanghae.justpotluck.domain.community.dto.request.PostUpdateDto;
import com.hanghae.justpotluck.domain.community.dto.response.PostResponseDto;
import com.hanghae.justpotluck.domain.community.service.PostService;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.exception.RestException;
import com.hanghae.justpotluck.global.result.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/api/community")
    public List<PostResponseDto> getPostAddress() {
        return postService.getPostAddress();
    }

    @GetMapping("/api/community/{postId}")
    public PostResponseDto findPost(@PathVariable Long postId) {
        postService.updateView(postId);
        return postService.findOne(postId);
    }

    @PostMapping("/api/community")
    public PostResponseDto savePost(@ModelAttribute @Valid PostRequestDto requestDto, Errors errors) {
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                throw new RestException(HttpStatus.BAD_REQUEST, "잘못된 입력방법입니다.");
            }
        }
        return postService.savePost(requestDto);
    }

    @DeleteMapping("/api/community/{postId}")
    public ResponseEntity<Success> deletePost(@PathVariable Long postId,@AuthenticationPrincipal User user) {
        postService.delete(postId);
        return new ResponseEntity<>(new Success(true, "게시글 삭제 성공"), HttpStatus.OK);
    }

    //커뮤니티 글 수정
    @PatchMapping("/api/community/{postId}")
    public PostResponseDto modifyPost(@PathVariable Long postId, @ModelAttribute PostUpdateDto requestDto) {
//        if (errors.hasErrors()) {
//            for (FieldError error : errors.getFieldErrors()) {
//                throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
//            }
//        }
        return postService.modify(postId, requestDto);
    }


    //나눔 완료
    // patch로 하고 uri를 새로운걸로
    @PatchMapping("/api/community/{postId}/complete")
    public ResponseEntity<Success> completedPost(@PathVariable("postId") Long postId, @RequestBody PostUpdateDto requestDto, @AuthenticationPrincipal User user, Errors errors) {
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                throw new RestException(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
            }
        }
        postService.modify(postId, requestDto, user.getName());
        return new ResponseEntity<>(new Success(true, "나눔 완료"), HttpStatus.OK);
    }
}
