package com.hanghae.justpotluck.domain.community.dto.response;

import com.hanghae.justpotluck.domain.comment.entity.Comments;
import com.hanghae.justpotluck.domain.community.entity.Posts;
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
    private String category;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
    private String location;
    private Double longitude;
    private Double latitude;
    private List<String> images;
    private int viewCount;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
    private List<Comments> commentList;

    public PostResponseDto(Posts post, List<String> postImages) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.createdAt = post.getCreatedAt();
        this.location = post.getLocation();
        this.viewCount = post.getViewCount();
//        this.location = post.getLocation();
//        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
        this.images = postImages;
        this.longitude = post.getLongitude();
        this.latitude = post.getLatitude();
        this.expiredAt = post.getExpiredAt();
        this.commentList = post.getCommentList();
    }
    public PostResponseDto(Posts post) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.createdAt = post.getCreatedAt();
        this.location = post.getLocation();
//        this.location = post.getLocation();
//        this.createdAt = post.getCreatedAt();
//        this.modifiedAt = post.getModifiedAt();
        this.longitude = post.getLongitude();
        this.latitude = post.getLatitude();
        this.expiredAt = post.getExpiredAt();
        this.commentList = post.getCommentList();
    }


}
