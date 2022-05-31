package com.hanghae.justpotluck.domain.user.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value="개인정보 수정 요청 정보")
public class UserUpdateRequest {

    //얘가 닉네임
    @ApiModelProperty(value = "닉네임", required = true)
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String name;
    
    //현재는 이메일 나중에 닉네임으로
//    private String email;
    //프사
//    private MultipartFile profileImage;

}