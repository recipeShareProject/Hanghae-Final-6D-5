package com.hanghae.justpotluck.domain.alarm.service;

import com.hanghae.justpotluck.domain.alarm.dto.AlarmResponseDto;
import com.hanghae.justpotluck.domain.alarm.entity.Alarm;
import com.hanghae.justpotluck.domain.alarm.entity.AlarmType;
import com.hanghae.justpotluck.domain.alarm.repository.AlarmRepository;
import com.hanghae.justpotluck.domain.board.entity.Board;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.hanghae.justpotluck.domain.user.repository.UserRepository;
import com.hanghae.justpotluck.global.security.UserPrincipal;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;


    public AlarmService(AlarmRepository alarmRepository, UserRepository userRepository, RedisTemplate redisTemplate, ChannelTopic channelTopic) {
        this.alarmRepository = alarmRepository;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
        this.channelTopic = channelTopic;
    }

    /* 게시물에 댓글이 등록되었을 경우 알림 보내기 */
    public static void generateNewReplyRecipeAlarm(User postOwner, User user, Board board) {
        Alarm alarm = Alarm.builder()
                .userName(postOwner.getName())
                .type(AlarmType.recipe_comment)
                .postId(board.getId())
                .isRead(false)
                .alarmMessage("[알림] ["
                        + board.getTitle()
                        + "] 레시피에 댓글이 등록되었습니다. 확인해보세요.")
                .build();

        /* 알림 메시지를 보낼 DTO 생성 */
        AlarmResponseDto alarmResponseDto = AlarmResponseDto.builder()
                .alarmId(alarm.getId())
                .type(alarm.getType().toString())
                .message("[알림] ["
                        + board.getTitle()
                        + "] 레시피에 댓글이 등록되었습니다. 확인해보세요.")
                .alarmTargetId(postOwner.getId())
                .isRead(alarm.getIsRead())
                .postId(alarm.getPostId())
                .build();

        /*-
         * redis로 알림메시지 pub, alarmRepository에 저장
         * 단, 게시글 작성자와 댓글 작성자가 일치할 경우는 제외
         */
//        if (!alarmResponseDto.getAlarmTargetId().equals(user.getId())) {
//            alarmRepository.save(alarm);
//            redisTemplate.convertAndSend(channelTopic.getTopic(),
//                    alarmResponseDto);
//        }

    }

    //뒷부분에 커뮤니티 알림도 해야함..//
//    public void generateNewReplyCommunityAlarm(User postOwner, User user, Posts post) {
//        Alarm alarm = Alarm.builder()
//                .userId(postOwner.getId())
//                .type(AlarmType.recipe_comment)
//                .postId(post.getId())
//                .isRead(false)
//                .alarmMessage("[알림] ["
//                        + post.getTitle()
//                        + "] 커뮤니티에 댓글이 등록되었습니다. 확인해보세요.")
//                .build();
//
//        /* 알림 메시지를 보낼 DTO 생성 */
//        AlarmResponseDto alarmResponseDto = AlarmResponseDto.builder()
//                .alarmId(alarm.getId())
//                .type(alarm.getType().toString())
//                .message("[알림] ["
//                        + board.getTitle()
//                        + "] 커뮤니티에 댓글이 등록되었습니다. 확인해보세요.")
//                .alarmTargetId(postOwner.getId())
//                .isRead(alarm.getIsRead())
//                .postId(alarm.getPostId())
//                .build();
//
//        /*-
//         * redis로 알림메시지 pub, alarmRepository에 저장
//         * 단, 게시글 작성자와 댓글 작성자가 일치할 경우는 제외
//         */
//        if (!alarmResponseDto.getAlarmTargetId().equals(user.getId())) {
//            alarmRepository.save(alarm);
//            redisTemplate.convertAndSend(channelTopic.getTopic(),
//                    alarmResponseDto);
//        }
//
//    }

    /* 알림 읽었을 경우 체크 */
    @Transactional
    public AlarmResponseDto alarmReadCheck(Long alarmId,
                                           UserPrincipal userDetails) {
        Alarm alarm = alarmRepository.getById(alarmId);
        User user = userDetails.getUser();
        alarm.setIsRead(true);
        alarmRepository.save(alarm);
        AlarmResponseDto alarmDto = new AlarmResponseDto();
        if (alarm.getType().equals(AlarmType.recipe_comment)) {
            alarmDto = AlarmResponseDto.builder()
                    .alarmId(alarm.getId())
                    .type(alarm.getType().toString())
                    .message(alarm.getAlarmMessage())
                    .isRead(alarm.getIsRead())
                    .postId(alarm.getPostId())
                    .build();
        }

        else if (alarm.getType().equals(AlarmType.community_comment)) {
            alarmDto = AlarmResponseDto.builder()
                    .alarmId(alarm.getId())
                    .type(alarm.getType().toString())
                    .message(alarm.getAlarmMessage())
                    .isRead(alarm.getIsRead())
                    .postId(alarm.getPostId())
                    .build();
        }
        return alarmDto;
    }
//    public List<AlarmResponseDto> getAlamList(User user) {
//        String userName = user.getName();
//
//
//        List<Alarm> alarmListPage = alarmRepository
//                .findAllByUserIdOrderByIdDesc(userName).getContent();
//
//        List<AlarmResponseDto> alarmResponseDtoList = new ArrayList<>();
//
//        for (Alarm alarm : alarmListPage) {
//            /* 게시물에 새로운 댓글이 등록되었을 때 */
//            if (alarm.getType().equals(AlarmType.recipe_comment)) {
//                AlarmResponseDto alarmDto = AlarmResponseDto.builder()
//                        .alarmId(alarm.getId())
//                        .type(alarm.getType().toString())
//                        .message(alarm.getAlarmMessage())
//                        .isRead(alarm.getIsRead())
//                        .postId(alarm.getPostId())
//                        .build();
//                alarmResponseDtoList.add(alarmDto);
//            }
//
//        }
//    return alarmResponseDtoList;
//    }
}
