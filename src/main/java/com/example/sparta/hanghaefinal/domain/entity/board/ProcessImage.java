package com.example.sparta.hanghaefinal.domain.entity.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;

import javax.persistence.*;

public class ProcessImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "review_id")
    private RecipeProcess process;

    private String imageUrl;
    private String storeFileName;
    private String image;

    private String origFileName;
    private String filePath;
    private Long fileSize;

    @Builder
    public ProcessImage(String imageUrl, String storeFileName, RecipeProcess process) {
        this.imageUrl = imageUrl;
        this.storeFileName = storeFileName;
        this.process = process;
    }
}
