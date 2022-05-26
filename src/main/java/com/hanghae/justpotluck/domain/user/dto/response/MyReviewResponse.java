package com.hanghae.justpotluck.domain.user.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hanghae.justpotluck.domain.review.entity.Review;
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
public class MyReviewResponse {

    @ApiModelProperty(value = "게시글 식별자", example = "1")
    private Long reviewId;

    @ApiModelProperty(value = "게시글 이름", example = "제목제목제목")
    private String title;

    private String comment;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @ApiModelProperty(value = "게시글 생성 시각")
    private LocalDateTime localDateTime;


    public static MyReviewResponse toMyReviewResponse(Review review) {
        return MyReviewResponse.builder()
                .reviewId(review.getId())
                .comment(review.getComment())
                .build();

    }

}