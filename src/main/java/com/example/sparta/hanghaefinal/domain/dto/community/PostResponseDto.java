package com.example.sparta.hanghaefinal.domain.dto.community;

import com.example.sparta.hanghaefinal.domain.entity.comment.Comments;
import com.example.sparta.hanghaefinal.domain.entity.community.Posts;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String image;
    private String category;
    private LocalDateTime expiredAt;
    private String location;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
    private List<Comments> commentList;

    public PostResponseDto(Posts post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.category = post.getCategory();
        this.location = post.getLocation();
//        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
        this.expiredAt = post.getExpiredAt();
        this.commentList = post.getCommentList();
    }

}
