package com.hanghae.justpotluck.domain.process.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "process_image")
@Entity
public class ProcessImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_image_id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "process_id")
    private RecipeProcess process;

    private String imageUrl;
    private String storeFileName;

//    private String origFileName;
//    private String filePath;
//    private Long fileSize;

    //    @Builder
//    public Image(String origFileName, String filePath, Long fileSize) {
//        this.origFileName = origFileName;
//        this.filePath = filePath;
//        this.fileSize = fileSize;
//    }

    @Builder
    public ProcessImage(String imageUrl, String storeFileName, RecipeProcess process) {
        this.imageUrl = imageUrl;
        this.storeFileName = storeFileName;
        this.process = process;
    }

    public ProcessImage(RecipeProcess process, String imageUrl) {
        this.process = process;
        this.imageUrl = imageUrl;
    }

//    private String image;

    public void setProcess(RecipeProcess process) {
        this.process = process;
        if (!process.getProcessImages().contains(this)) {
            process.getProcessImages().add(this);
        }
    }
}
