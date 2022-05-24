package com.hanghae.justpotluck.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
//    private final StompHandler stompHandler; // jwt 토큰 인증 핸들러

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp", "/ws-stompAlarm")
                .setAllowedOriginPatterns("*")
                .withSockJS(); //sock.js를 통하여 낮은 버전의 브라우저에서도 websocket 이 동작할수 있게 한다
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); // prefix /sub 로 수신 메시지 구분
        //여기로 데이터가 들어온 경우 messageMapping 으로 JUMP
        registry.setApplicationDestinationPrefixes("/pub"); // prefix /pub 로 발행 요청
    }

    // StompHandler 인터셉터 설정
    // StompHandler 가 Websocket 앞단에서 token 및 메시지 TYPE 등을 체크할 수 있도록 다음과 같이 인터셉터로 설정한다
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(stompHandler); // 핸들러 등록
//    }
}