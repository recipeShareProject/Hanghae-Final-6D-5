package com.hanghae.justpotluck.domain.user.controller;

import com.hanghae.justpotluck.domain.user.dto.response.MyBoardResponse;
import com.hanghae.justpotluck.domain.user.dto.response.MyCommentResponse;
import com.hanghae.justpotluck.domain.user.dto.response.MyPostResponse;
import com.hanghae.justpotluck.domain.user.dto.response.MyReviewResponse;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.domain.user.service.UserService;
import com.hanghae.justpotluck.global.exception.ResourceNotFoundException;
import com.hanghae.justpotluck.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {


    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user/me")
    public User getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getUsername()));
    }

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userPrincipal.getUsername();
    }


    @GetMapping("/user/me/board")
    @ApiResponse(responseCode = "200", description = "내가 쓴 글이 정상적으로 조회된 경우")
    public ResponseEntity<Page<MyBoardResponse>> findMyBoard(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyBoard(pageable));
    }

    @GetMapping("/user/me/community")
    @ApiResponse(responseCode = "200", description = "내가 쓴 글이 정상적으로 조회된 경우")
    public ResponseEntity<Page<MyPostResponse>> findMyPosts(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyPosts(pageable));
    }

    @GetMapping("/user/me/review")
    @ApiResponse(responseCode = "200", description = "내가 쓴 글이 정상적으로 조회된 경우")
    public ResponseEntity<Page<MyReviewResponse>> findMyReview(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyReview(pageable));
    }

    @GetMapping("/user/me/comment")
    @ApiResponse(responseCode = "200", description = "내가 쓴 글이 정상적으로 조회된 경우")
    public ResponseEntity<Page<MyCommentResponse>> findMyComment(Pageable pageable) {
        return ResponseEntity.ok(userService.findMyComment(pageable));
    }


//    @GetMapping("/user/me/board")

//
//    @GetMapping("/user/mypage")
//    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest updateRequest) {
//        return ResponseEntity.ok(UserResponse.toUserResponse(userService.update(updateRequest)));
//    }

}