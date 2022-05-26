package com.hanghae.justpotluck.domain.community.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    private Long postId;
    private String category;
    private String expiredAt;
//    private String category;
    private String title;
    private List<String> tags;
    private String content;
    private List<String> saveImageUrl = new ArrayList<>();
    private List<MultipartFile> images = new ArrayList<>();
//    @Builder
//    public PostUpdateDto(String category) {
//        this.category = category;
//    }
}
