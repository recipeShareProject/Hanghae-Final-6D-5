package com.hanghae.justpotluck.domain.user.dto.request;

import javax.validation.constraints.NotNull;

public class LocationRequest {
    @NotNull(message = "위도(lat) 정보가 없습니다")
    private double lat;


    @NotNull(message = "위도(lat) 정보가 없습니다")
    private double lon;

    @NotNull(message = "행정구역 위치 정보(address)가 없습니다")
    private String address;
}
