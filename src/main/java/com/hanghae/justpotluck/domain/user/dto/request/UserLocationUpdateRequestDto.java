package com.hanghae.justpotluck.domain.user.dto.request;

import com.hanghae.justpotluck.domain.user.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel
public class UserLocationUpdateRequestDto {

    private double latitude;
    private double longitude;
    private String address;

}