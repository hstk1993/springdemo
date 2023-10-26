package com.example.springdemo.api

import jakarta.websocket.OnClose
import jakarta.websocket.OnMessage
import jakarta.websocket.OnOpen
import jakarta.websocket.Session
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint
import org.springframework.stereotype.Component
import java.util.concurrent.CopyOnWriteArraySet

@Component
@ServerEndpoint("/websocket/{userId}")
class WebSocketServer {
    companion object {
        @JvmStatic
        val SESSIONS = CopyOnWriteArraySet<Session>()

        @JvmStatic
        val SESSION_POOL = HashMap<String, Session>()
    }


    @OnOpen
    fun onOpen(session: Session, @PathParam(value = "userId") userId: String) {
        try {
            SESSIONS.add(session);
            SESSION_POOL[userId] = session;
        } catch (e: Exception) {
            e.printStackTrace();
        }

    }

    @OnClose
    fun onClose(session: Session) {
        SESSIONS.remove(session)
    }

    @OnMessage
    fun onMessage(msg: String) {
        senAllMessage(msg)
    }

    fun senAllMessage(message: String) {
        for (session in SESSIONS) {
            try {
                if (session.isOpen) {
                    session.asyncRemote.sendText(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendOneMessage(userId: String, message: String) {
        val session = SESSION_POOL[userId]
        if (session != null && session.isOpen) {
            try {
                synchronized(session) {
                    session.asyncRemote.sendText(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendMoreMessage(userIds: MutableList<String>, message: String) {
        for (userId in userIds) {
            val session = SESSION_POOL[userId]
            if (session != null && session.isOpen) {
                try {
                    if (session.isOpen) {
                        session.asyncRemote.sendText(message)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

}