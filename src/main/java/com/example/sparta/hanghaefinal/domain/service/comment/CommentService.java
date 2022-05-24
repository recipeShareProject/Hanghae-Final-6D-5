package com.example.sparta.hanghaefinal.domain.service.comment;

import com.example.sparta.hanghaefinal.advice.RestException;
import com.example.sparta.hanghaefinal.domain.dto.comment.CommentRequestDto;
import com.example.sparta.hanghaefinal.domain.dto.comment.CommentUpdateDto;
import com.example.sparta.hanghaefinal.domain.entity.comment.Comments;
import com.example.sparta.hanghaefinal.domain.entity.community.Posts;
import com.example.sparta.hanghaefinal.domain.repository.comment.CommentRepository;
import com.example.sparta.hanghaefinal.domain.repository.community.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void save(Long postId, CommentRequestDto requestDto){
        Posts post = postRepository.findByPostId(postId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다."));

        Comments comment = Comments.builder()
                .content(requestDto.getContent())
                .post(post)
                .parent(null)
                .build();
        comment.confirmPost(post);
        commentRepository.save(comment);
    }

    public void saveReComment(Long commentId, CommentRequestDto requestDto){
      Comments parent = commentRepository.findByCommentId(commentId).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "해당 commentId가 존재하지 않습니다."));

      Comments comment = Comments.builder()
                .content(requestDto.getContent())
                .post(parent.getPost())
                .parent(parent)
                .build();
      comment.confirmParent(parent);

      commentRepository.save(comment);
    }

    public void modify(Long commentId, CommentUpdateDto requestDto){
        Comments comment = commentRepository.findByCommentId(commentId).orElseThrow(
                () -> new RestException(HttpStatus.NOT_FOUND, "해당 postId가 존재하지 않습니다.")
        );
        comment.updateContent(requestDto.getContent());
    }

    public void remove(Long id) throws Exception {
        Comments comment = commentRepository.findByCommentId(id).orElseThrow(() -> new Exception("댓글이 없습니다."));
        comment.remove();
        List<Comments> removableCommentList = comment.findRemovableList();
        removableCommentList.forEach(removableComment -> commentRepository.delete(removableComment));
    }
}
