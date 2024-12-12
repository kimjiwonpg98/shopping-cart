package kr.co.shoppingcart.cart.config.websocket

import kr.co.shoppingcart.cart.auth.JwtProvider
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Component

@Component
class FilterChannelInterceptor(
    private val jwtProvider: JwtProvider,
) : ChannelInterceptor {
    override fun preSend(
        message: Message<*>,
        channel: MessageChannel,
    ): Message<*> {
        val accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
                ?: throw CustomException.responseBody(ExceptionCode.E_401_000)

        if (accessor.command == StompCommand.CONNECT) {
            val token =
                accessor.getFirstNativeHeader("Authorization")?.removePrefix("Bearer ")
                    ?: throw CustomException.responseBody(ExceptionCode.E_401_000)
            val payload = jwtProvider.verifyToken(token)
            accessor.setNativeHeader("userId", payload.identificationValue)
        }

        return message
    }
}
