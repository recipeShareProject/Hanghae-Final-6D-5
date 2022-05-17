package com.example.sparta.hanghaefinal.domain.dto.review;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ReviewUploadImageRequest {

    private Long boardId;
    private Long reviewId;
    private List<MultipartFile> images = new ArrayList<>();
}
