package com.hanghae.justpotluck.domain.community.entity;

import com.hanghae.justpotluck.global.config.Timestamped;
import com.hanghae.justpotluck.domain.community.dto.request.PostRequestDto;
import com.hanghae.justpotluck.domain.comment.entity.Comments;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;


@Entity
@DynamicUpdate
@DynamicInsert
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Posts extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column
    private String image;


    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "posts")
    @Column
    List<Tag> tags = new ArrayList<>();

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String location;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comments> commentList = new ArrayList<>();

    @ManyToOne
    private User user;

    public void addComment(Comments comment) {
        this.commentList.add(comment);
    }


    @Builder
    public Posts(String title, String content, String image, String category, Double longitude, Double latitude, LocalDateTime expiredAt, List<Tag> tags){
        this.title = title;
        this.content = content;
        this.image = image;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.expiredAt = expiredAt;
        this.tags = tags;
    }

    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.image = requestDto.getImagePath();
        this.category = requestDto.getCategory();
        this.expiredAt = LocalDateTime.parse(requestDto.getExpiredAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public void update(String category) {
        this.category = category;
    }
}