package com.example.sparta.hanghaefinal.domain.dto.comment;

import com.example.sparta.hanghaefinal.domain.entity.comment.Comments;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@ApiModel(value = "댓글 생성 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    @ApiModelProperty(value = "댓글", notes = "댓글을 입력해주세요", required = true, example = "my comment")
    @NotBlank(message = "댓글을 입력해주세요.")
    @Size(max = 100)
    private String content;

    @ApiModelProperty(value = "게시글 아이디", notes = "게시글 아이디를 입력해주세요", example = "7")
    @NotNull(message = "게시글 아이디를 입력해주세요.")
    @Positive(message = "올바른 게시글 아이디를 입력해주세요.")
    private Long postId;

    @ApiModelProperty(value = "부모 댓글 아이디", notes = "댓글 아이디를 입력해주세요", example = "7")
    private Long parentId;

    @Builder
    CommentRequestDto(Comments comment){
        this.content = comment.getContent();
        this.postId = comment.getPost().getPostId();
        this.parentId = comment.getParent().getCommentId();
    }
}
