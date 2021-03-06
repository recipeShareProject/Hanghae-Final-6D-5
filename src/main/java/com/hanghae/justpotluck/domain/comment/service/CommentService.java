package com.hanghae.justpotluck.domain.comment.service;

import com.hanghae.justpotluck.domain.comment.dto.response.CommentResponseDto;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.exception.RestException;
import com.hanghae.justpotluck.domain.comment.dto.request.CommentRequestDto;
import com.hanghae.justpotluck.domain.comment.dto.response.CommentUpdateDto;
import com.hanghae.justpotluck.domain.comment.entity.Comments;
import com.hanghae.justpotluck.domain.community.entity.Posts;
import com.hanghae.justpotluck.domain.comment.repository.CommentRepository;
import com.hanghae.justpotluck.domain.community.repository.PostRepository;
import com.hanghae.justpotluck.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserUtil userUtil;

    @Transactional
    public CommentResponseDto save(Long postId, CommentRequestDto requestDto){
        User user = userUtil.findCurrentUser();
        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다."));

        Comments comment = Comments.builder()
                .comment(requestDto.getComment())
                .post(post)
                .parent(null)
                .nickname(user.getName())
                .user(user)
                .build();
        comment.confirmPost(post);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto saveReComment(Long postId, Long commentId, CommentRequestDto requestDto){
        User user = userUtil.findCurrentUser();
        Posts post = postRepository.findByPostId(postId).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다."));
        Comments parent = commentRepository.findByPostIdAndCommentId(postId, commentId).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "해당 commentId가 존재하지 않습니다."));

        Comments comment = Comments.builder()
                .comment(requestDto.getComment())
                .post(post)
                .parent(parent)
                .nickname(user.getName())
                .user(user)
                .build();

        comment.confirmParent(parent);

        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto modify(Long postId, Long commentId, CommentUpdateDto requestDto){
        User user = userUtil.findCurrentUser();
        Comments comment = commentRepository.findByPostIdAndCommentId(postId, commentId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );

        comment.updateContent(requestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void remove(Long id) throws Exception {
        User user = userUtil.findCurrentUser();
        Comments comment = commentRepository.findByCommentId(id).orElseThrow(() -> new Exception("댓글이 없습니다."));
        comment.remove();
        List<Comments> removableCommentList = comment.findRemovableList();
        removableCommentList.forEach(removableComment -> commentRepository.delete(removableComment));
    }
}
