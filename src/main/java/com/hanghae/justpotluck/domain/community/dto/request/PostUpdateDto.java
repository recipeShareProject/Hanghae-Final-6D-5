package com.hanghae.justpotluck.domain.community.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    private Long postId;
    private String category;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expiredAt;
    private String title;
    private ArrayList<String> tags;
    private String content;
    private String address;
    private double latitude;
    private double longitude;
    private List<String> saveImageUrl = new ArrayList<>();
    private List<MultipartFile> images = new ArrayList<>();
}
