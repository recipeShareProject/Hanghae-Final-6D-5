package com.hanghae.justpotluck.domain.alarm.entity;
import com.hanghae.justpotluck.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hanghae.justpotluck.global.config.Timestamped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Alarm extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long alarmId;
//알람 아이디
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
//알람 받는 사람
    @Column(name = "sender_nickName")
    private String senderNickName;
//알람 보낸 사람
    @Column
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;
//알림 종류 보드인지 매거진인지 전체 공지인지 홍보 알림인지 등등
    @Column
    private Long id;


    @Column
    private String title;

    @Enumerated(EnumType.STRING)
    private  FReadingStatus readingStatus;


    public Alarm(User receiver, String senderNickName, AlarmType alarmType, Long id, String title) {
        this.receiver = receiver;
        this.senderNickName = senderNickName;
        this.alarmType = alarmType;
        this.id = id;
        this.title = title;
        this.readingStatus = ReadingStatus.N;
    }


    public void changeReadingStatus(ReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }

    public LocalDateTime modifiedAt() {
        return null;
    }
}
