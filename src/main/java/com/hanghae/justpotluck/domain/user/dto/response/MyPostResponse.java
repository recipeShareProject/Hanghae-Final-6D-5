package com.hanghae.justpotluck.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.justpotluck.domain.community.entity.Posts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("마이페이지 내가 쓴 글 정보")
public class MyPostResponse {

    @ApiModelProperty(value = "게시글 식별자", example = "1")
    private Long postId;

    @ApiModelProperty(value = "카테고리 이름", example = "자산관리 Q&A")
    private String category;

    @ApiModelProperty(value = "게시글 이름", example = "제목제목제목")
    private String title;

    private String location;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(value = "게시글 생성 시각")
    private LocalDateTime localDateTime;


    public static MyPostResponse toMyPostResponse(Posts post) {
        return MyPostResponse.builder()
                .postId(post.getPostId())
                .category(post.getCategory())
                .title(post.getTitle())
                .location(post.getLocation())
                .build();

    }
}
