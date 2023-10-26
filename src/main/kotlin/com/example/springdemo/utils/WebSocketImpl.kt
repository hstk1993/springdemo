package com.example.springdemo.utils

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.atomic.AtomicInteger


class WebSocketImpl : WebSocket {
    val connectionCount = AtomicInteger(0)
    val sessions = CopyOnWriteArraySet<WebSocketSession>()

    override fun handleOpen(session: WebSocketSession) {
        sessions.add(session)
        val count = connectionCount.incrementAndGet()
    }

    override fun handleClose(session: WebSocketSession?) {
        sessions.remove(session)
        connectionCount.decrementAndGet()
    }

    override fun handleMessage(session: WebSocketSession?, message: String?) {

    }

    override fun sendMessage(session: WebSocketSession?, message: String?) {
        sendMessage(session, TextMessage(message!!))
    }

    override fun sendMessage(userId: String?, message: TextMessage?) {

        val userSession = sessions.stream().filter {
            if (!it.isOpen) {
                return@filter false
            }
            val attributes = it.attributes
            if (attributes.containsKey("uid")) {
                return@filter false
            }
            val uid = attributes["uid"]!! as String
            return@filter uid == userId
        }.findFirst()
        if (userSession.isPresent) {
            userSession.get().sendMessage(message!!)
        }
    }

    override fun sendMessage(userId: String?, message: String?) {
        sendMessage(userId, TextMessage(message!!))
    }

    override fun sendMessage(session: WebSocketSession?, message: TextMessage?) {
        session!!.sendMessage(message!!)
    }

    override fun broadCast(message: String?) {
        for (session in sessions) {
            if (!session.isOpen) {
                continue
            }
            sendMessage(session, message)
        }
    }

    override fun broadCast(message: TextMessage?) {

    }

    override fun handleError(session: WebSocketSession?, error: Throwable?) {

    }

    override fun getSessions(): MutableSet<WebSocketSession> {
        return  sessions
    }

    override fun getConnectionCount(): Int {
        return connectionCount.get()
    }
}