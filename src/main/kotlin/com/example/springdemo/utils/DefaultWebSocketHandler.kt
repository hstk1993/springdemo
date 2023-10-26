package com.example.springdemo.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession


class DefaultWebSocketHandler : WebSocketHandler {
    @Autowired
    private lateinit var webSocket: WebSocket
    override fun afterConnectionEstablished(session: WebSocketSession) {
        webSocket.handleOpen(session)
    }

    override fun handleMessage(session: WebSocketSession, message: WebSocketMessage<*>) {
        if (message is TextMessage) {
            webSocket.handleMessage(session, message.payload)
        }
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {

    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        webSocket.handleClose(session)
    }

    override fun supportsPartialMessages(): Boolean {
        return false
    }
}