package com.hanghae.justpotluck.domain.alarm.controller;


import com.hanghae.justpotluck.domain.alarm.dto.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlarmController {}

//    private final AlarmService alarmService;
//
//    /* 회원별 전체 알람 리스트 발송 */
//    @GetMapping("/api/alarm")
//    public List<AlarmResponseDto> getAlarmList(
//            @RequestParam int page,
//            @RequestParam int size,
//            @AuthenticationPrincipal UserPrincipal userDetails) {
//
//        User user = userDetails.getUser();
//        return alarmService.getAlamList(user, page, size);
//    }
//
//    /* 알림 읽음 확인 */
//    @PostMapping("/api/alarm/{alarmId}")
//    public AlarmResponseDto alarmReadCheck(
//            @PathVariable Long alarmId,
//            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//
//        return alarmService.alarmReadCheck(alarmId, userDetails);
//    }
//}