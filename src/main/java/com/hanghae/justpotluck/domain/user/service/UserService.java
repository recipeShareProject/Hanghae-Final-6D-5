package com.hanghae.justpotluck.domain.user.service;

import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.board.repository.BoardRepository;
import com.hanghae.justpotluck.domain.board.repository.BookmarkRepository;
import com.hanghae.justpotluck.domain.comment.entity.Comments;
import com.hanghae.justpotluck.domain.comment.repository.CommentRepository;
import com.hanghae.justpotluck.domain.community.entity.Posts;
import com.hanghae.justpotluck.domain.community.repository.PostRepository;
import com.hanghae.justpotluck.domain.review.entity.Review;
import com.hanghae.justpotluck.domain.review.repository.ReviewRepository;
import com.hanghae.justpotluck.domain.user.dto.response.MyBoardResponse;
import com.hanghae.justpotluck.domain.user.dto.response.MyCommentResponse;
import com.hanghae.justpotluck.domain.user.dto.response.MyPostResponse;
import com.hanghae.justpotluck.domain.user.dto.response.MyReviewResponse;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.global.exception.CustomException;
import com.hanghae.justpotluck.global.exception.ErrorCode;
import com.hanghae.justpotluck.global.security.TokenAuthenticationFilter;
import com.hanghae.justpotluck.global.security.TokenProvider;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserUtil userUtil;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TokenProvider tokenProvider;
    private final TokenAuthenticationFilter authenticationFilter;


    public User getUser() {
        return userUtil.findCurrentUser();
    }

    public User getUser(String email) {
        return findUser(email);
    }

    private User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }

    public Boolean isDuplicateEmail(String userEmail) {
        if (userRepository.existsByEmail(userEmail)) {
            throw new CustomException(ErrorCode.ALREADY_EMAIL_EXISTS);
        } else {
            return false;
        }
    }

    public Page<MyPostResponse> findMyPosts(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        Page<Posts> post = postRepository.findByUserOrderByPostIdDesc(user, pageable);

        List<MyPostResponse> postResponses =
                post.stream()
                        .map(MyPostResponse::toMyPostResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(postResponses, pageable, post.getTotalElements());
    }

    public Page<MyBoardResponse> findMyBoard(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        Page<Board> board = boardRepository.findByUserOrderByIdDesc(user, pageable);

        List<MyBoardResponse> boardResponses =
                board.stream()
                        .map(MyBoardResponse::toMyBoardResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(boardResponses, pageable, board.getTotalElements());
    }

    public Page<MyCommentResponse> findMyComment(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        Page<Comments> comment = commentRepository.findByUserOrderByCommentIdDesc(user, pageable);

        List<MyCommentResponse> commentResponses =
                comment.stream()
                        .map(MyCommentResponse::toMyCommentResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(commentResponses, pageable, comment.getTotalElements());
    }
    public Page<MyReviewResponse> findMyReview(Pageable pageable) {
        User user = userUtil.findCurrentUser();
        Page<Review> review = reviewRepository.findByUserOrderByIdDesc(user, pageable);

        List<MyReviewResponse> reviewResponses =
                review.stream()
                        .map(MyReviewResponse::toMyReviewResponse)
                        .collect(Collectors.toList());

        return new PageImpl<>(reviewResponses, pageable, review.getTotalElements());
    }




//    public Page<MyCommentResponse> findMyComments(Pageable pageable) {
//        User user = userUtil.findCurrentUser();
//        Page<Comment> comments = commentRepository.findByUserOrderByIdDesc(user, pageable);
//
//        List<MyCommentResponse> commentResponses =
//                comments.stream()
//                        .map(MyCommentResponse::toMyCommentResponse)
//                        .collect(Collectors.toList());
//
//        return new PageImpl<>(commentResponses, pageable, comments.getTotalElements());
//    }
//
//    public Page<MyBookmarkResponse> findMyScraps(Pageable pageable) {
//        User user = userUtil.findCurrentUser();
//        Page<Scrap> scraps = scrapRepository.findByUserOrderByIdDesc(user, pageable);
//
//        List<MyScrapResponse> scrapResponses =
//                scraps.stream()
//                        .map(MyScrapResponse::toMyScrapResponse)
//                        .collect(Collectors.toList());
//
//        return new PageImpl<>(scrapResponses, pageable, scraps.getTotalElements());
//    }



//    @Transactional
//    public User update(UserUpdateRequest userUpdateRequest) {
//        User user = userUtil.findCurrentUser();
//        // 닉네임 중복 확인
//        if (userRepository.existsByNickname(userUpdateRequest.getNickname())) {
//            throw new CustomException(ErrorCode.ALREADY_NICKNAME_EXISTS);
//        }
//
//        user.update(userUpdateRequest);
//        return user;
//    }

}
