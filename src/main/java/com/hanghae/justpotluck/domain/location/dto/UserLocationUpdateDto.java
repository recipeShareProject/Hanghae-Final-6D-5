package com.hanghae.justpotluck.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLocationUpdateDto {
    private double latitude;
    private double longitude;
    private String address;
}