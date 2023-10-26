package com.example.springdemo.api

import com.example.springdemo.utils.WebSocket
import com.example.springdemo.utils.DefaultWebSocketHandler
import com.example.springdemo.utils.WebSocketImpl
import com.example.springdemo.utils.WebSocketInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfiguration : WebSocketConfigurer {
    @Bean
    fun defaultWebSocketHandler(): DefaultWebSocketHandler {
        return DefaultWebSocketHandler()
    }

    @Bean
    fun webSocket(): WebSocket {
        return WebSocketImpl();
    }


    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(DefaultWebSocketHandler(), "ws/message").addInterceptors(WebSocketInterceptor())
            .setAllowedOrigins("*")
    }
}