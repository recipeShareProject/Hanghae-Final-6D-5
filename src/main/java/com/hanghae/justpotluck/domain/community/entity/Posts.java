package com.hanghae.justpotluck.domain.community.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hanghae.justpotluck.domain.comment.entity.Comments;
import com.hanghae.justpotluck.domain.community.dto.request.PostRequestDto;
import com.hanghae.justpotluck.domain.community.dto.request.PostUpdateDto;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;


@Entity
//@DynamicUpdate
//@DynamicInsert
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Posts extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @JsonBackReference
    @OneToMany(
            mappedBy = "posts",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<PostImage> images = new ArrayList<>();

    private String category;

    ArrayList<String> tags = new ArrayList<>();

    //https 인증 받고 geolcation api
    //    @Column(nullable = false)
    /* 위도 데이터 / 경도 데이터 / 행정구역 위치 정보 / 데이터 없을 시 경고문 보내주기 */

    private Double latitude;

    private Double longitude;

    @Column(name = "address")
    private String address;


    private int viewCount;

    //    @Column(nullable = false)
//    @Embedded
//    private Location location;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = ALL, orphanRemoval = true)
    private List<Comments> commentList = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void addComment(Comments comment) {
        this.commentList.add(comment);
    }


    @Builder
    public Posts(User user, String title, String content,String category, String address, Double longitude, Double latitude, LocalDateTime expiredAt, ArrayList<String> tags){
        this.user = user;
        this.title = title;
        this.content = content;
        this.category = category;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.expiredAt = expiredAt;
        this.tags = tags;
    }

    public static Posts createPost(PostRequestDto requestDto, User user) {
        return Posts.builder()
                .title(requestDto.getTitle())
                .category(requestDto.getCategory())
                .content(requestDto.getContent())
                .address(requestDto.getAddress())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .tags(requestDto.getTags())
                .expiredAt(requestDto.getExpiredAt())
                .user(user)
                .build();
    }
    public void update(PostUpdateDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
        this.expiredAt = requestDto.getExpiredAt();
        this.tags = requestDto.getTags();
        this.address = requestDto.getAddress();
        this.latitude = requestDto.getLatitude();
        this.longitude = requestDto.getLongitude();
//        this.expiredAt = LocalDateTime.parse(requestDto.getExpiredAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
//        this.expiredAt = LocalDateTime.parse(requestDto.getExpiredAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public void update(String category) {
        this.category = category;
    }
}
