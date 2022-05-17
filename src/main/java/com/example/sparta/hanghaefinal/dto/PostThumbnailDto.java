package com.example.sparta.hanghaefinal.dto;

import com.example.sparta.hanghaefinal.domain.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostThumbnailDto {

    private Long communityId;
    private String category;
    private String imagePath;
    private String title;
    private String location;
    private LocalDateTime createdAt;

    public PostThumbnailDto(Posts post) {
        this.communityId = post.getPostId();
        this.title = post.getTitle();
        this.imagePath = post.getImage();
        this.category = post.getCategory();
        this.location = post.getLocation();
        this.createdAt = post.getCreatedAt();
    }
}


