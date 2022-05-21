package com.hanghae.justpotluck.global.config;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

//메시지 브로커 추가함
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    //메세지 브로커 목적지 설정, 메시지 주고 받을때 엔드포인트에 대한 prefix를 정해주는 것
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");// 서버가 목적지 일때(Client -> Server 메시지 전송시 Endpoint)
        registry.setApplicationDestinationPrefixes("/app"); //클라이언트가 Subscribe 할떄(Server -> Client 메시지 전송 시 Endpoint)
    }

    @Override
    //인터페이스를 상속받아 아래 메서드를 활용하여 커스터마이징
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hanghaefinal")
                .setAllowedOriginPatterns("*")
                .withSockJS(); //SockJS 사용하기 위해 추가
    }

}