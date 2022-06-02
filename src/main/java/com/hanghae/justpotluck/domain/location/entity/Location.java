//package com.hanghae.justpotluck.domain.location.entity;
//
//import com.hanghae.justpotluck.domain.community.dto.request.PostRequestDto;
//import com.hanghae.justpotluck.domain.user.dto.request.UserLocationUpdateRequestDto;
//import com.hanghae.justpotluck.domain.user.entity.User;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Embeddable;
//
//@Getter
//@NoArgsConstructor
//@Embeddable
//public class Location {
//    private String address;
//    private double latitude;
//    private double longitude;
//
//    public Location(UserLocationUpdateRequestDto updateDto) {
//        this.latitude = updateDto.getLatitude();
//        this.longitude = updateDto.getLongitude();
//        this.address = updateDto.getAddress();
//    }
//
//    public Location(PostRequestDto requestDto) {
//        this.latitude = requestDto.getLatitude();
//        this.longitude = requestDto.getLongitude();
//        this.address = requestDto.getAddress();
//    }
//
//    public Location(String address, double latitude, double longitude) {
//        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
//
//
//
//    public void update(UserLocationUpdateRequestDto requestDto, User user){
//        this.address=requestDto.getAddress();
//        this.latitude=requestDto.getLatitude();
//        this.longitude= requestDto.getLongitude();
//    }
//}