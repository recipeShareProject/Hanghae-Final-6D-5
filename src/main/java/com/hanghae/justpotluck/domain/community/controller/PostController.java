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

    //게시글 목록 불러오기(유저 기준으로 반경 5km 이내의 게시글 필터링) 미구현
//    @GetMapping("/api/community")
//    public List<PostThumbnailDto> findPostAll(HttpServletRequest request, @AuthenticationPrincipal User user) {
//        int pagingCnt;
//        if (request.getHeader("PAGING_CNT") == null) {
//            pagingCnt = 0;
//        } else {
//            pagingCnt = Integer.parseInt(request.getHeader("PAGING_CNT"));
//        }
//
//        if (user == null) {
////            기본 좌표 기준으로 보여줌
//            return postService.findPosts(pagingCnt, 0.0, 0.0);
//        } else {
//            return postService.findPosts(pagingCnt, user.getLongitude(), user.getLatitude());
//        }
//
//    }

    @GetMapping("/api/community")
    public List<PostResponseDto> getPostAddress() {
        return postService.getPostAddress();
    }

    @GetMapping("/api/community/{postId}")
    public PostResponseDto findPost(@PathVariable Long postId) {
        postService.updateView(postId);
//        처리방법 생각해보기 (이미 로그인 없으면 못들어오는데 한 번 더 확인을 해야하는가?)
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
//        return new ResponseEntity<>(new Success(true, "게시글 저장 성공"), HttpStatus.OK);
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
