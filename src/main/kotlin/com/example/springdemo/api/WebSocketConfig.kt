package com.example.springdemo.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.server.standard.ServerEndpointExporter

@Configuration
class WebSocketConfig {
    @Bean
    fun createWebSocketContainer(): ServerEndpointExporter {
        return ServerEndpointExporter()
    }

}