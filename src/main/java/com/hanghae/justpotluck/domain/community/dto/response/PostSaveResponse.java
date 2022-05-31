package com.hanghae.justpotluck.domain.community.dto.response;

import com.hanghae.justpotluck.domain.community.entity.Posts;
import com.hanghae.justpotluck.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveResponse {
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
    private List<String> tags;
    private User user;
    private List<String> imageUrl = new ArrayList<>();

    public PostSaveResponse(Posts post, List<String> postImages) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.location = post.getLocation();
        this.latitude = post.getLatitude();
        this.longitude = post.getLongitude();
        this.viewCount = post.getViewCount();
        this.tags = post.getTags();
        this.user = post.getUser();
        this.images = postImages;
    }
}
