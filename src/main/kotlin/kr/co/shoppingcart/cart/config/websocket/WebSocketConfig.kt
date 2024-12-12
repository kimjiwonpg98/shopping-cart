package kr.co.shoppingcart.cart.config.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val filterChannelInterceptor: FilterChannelInterceptor,
) : WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry
            .addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .withSockJS()
        registry
            .addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        // 조회 시 필요
        // @Controller 객체의 @MessageMapping 메서드로 라우팅, 클라이언트가 서버로 메시지 보낼 URL 접두사(pub)
        registry.enableSimpleBroker("/get")

        // 수정 시 필요
        // /topic 로 클라이언트로 메시지 전달(sub)
        registry.setApplicationDestinationPrefixes("/update")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(filterChannelInterceptor)
    }
}
