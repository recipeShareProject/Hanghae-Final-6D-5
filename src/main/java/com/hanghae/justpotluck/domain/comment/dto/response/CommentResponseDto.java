package com.hanghae.justpotluck.domain.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hanghae.justpotluck.domain.comment.entity.Comments;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private List<CommentResponseDto> children;
    private boolean deleted;
    //    private String writer;
    private String nickname;

    public CommentResponseDto(Comments comment) {
        this.commentId = comment.getCommentId();
        this.comment = comment.getComment();
        this.nickname = comment.getUser().getName();
    }

}
