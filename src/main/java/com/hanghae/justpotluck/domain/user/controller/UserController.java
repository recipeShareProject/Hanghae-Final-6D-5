package com.hanghae.justpotluck.domain.user.controller;

import com.hanghae.justpotluck.domain.user.dto.request.UserUpdateRequest;
import com.hanghae.justpotluck.domain.user.dto.response.*;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.domain.user.service.UserService;
import com.hanghae.justpotluck.global.security.UserPrincipal;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    //test controller
    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userPrincipal.getUsername();
    }

    @ApiOperation(value = "로그아웃",
            notes = "엑세스, 리프레시 토큰 검증 후 로그아웃을 진행 합니다. 로그아웃 성공시 true를 반환합니다. " +
                    "헤더(Bearer)에 엑세스 토큰 주입을 필요로 하며, 리프레시 토큰을 쿠키(쿠키명: refresh_token)로 주입해야 합니다.")
    @PostMapping("/logout")
    @ApiResponse(responseCode = "200", description = "정상적으로 로그아웃 된 경우")
    public ResponseEntity<Boolean> logout(HttpServletRequest request) {
        return ResponseEntity.ok(userService.logout(request));
    }


    @PatchMapping("/user/me/update")
    @ApiOperation(value = "개인정보 수정",
            notes = "현재 사용자의 개인정보를 업데이트 합니다. 헤더(Bearer)에 사용자 토큰 주입을 필요로 합니다.")
    @ApiResponse(responseCode = "200", description = "사용자의 개인정보를 정상적으로 수정한 경우")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(UserResponse.toUserResponse(userService.update(userUpdateRequest)));
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