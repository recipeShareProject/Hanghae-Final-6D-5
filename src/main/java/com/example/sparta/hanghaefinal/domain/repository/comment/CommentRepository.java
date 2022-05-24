package com.example.sparta.hanghaefinal.domain.repository.comment;

import com.example.sparta.hanghaefinal.domain.entity.comment.Comments;
import com.example.sparta.hanghaefinal.domain.entity.community.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    @Query("select c from Comments c left join fetch c.parent where c.commentId = :id")
    Optional<Comments> findCommentByIdWithParent(@Param("id") Long id);

//    @Query("select c from Comments c where c.post.postId = :postId")
//    Optional<Comments> findByPostId(@Param("postId") Long postId);

    Optional<Comments> findByCommentId(Long CommentId);


    void delete(Comments comment);
}
