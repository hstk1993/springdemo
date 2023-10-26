package com.example.springdemo.utils

import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.io.IOException
import java.util.concurrent.atomic.AtomicInteger


interface WebSocket {
    /**
     * 会话开始回调
     *
     * @param session 会话
     */
    fun handleOpen(session: WebSocketSession)

    /**
     * 会话结束回调
     *
     * @param session 会话
     */
    fun handleClose(session: WebSocketSession?)

    /**
     * 处理消息
     *
     * @param session 会话
     * @param message 接收的消息
     */
    fun handleMessage(session: WebSocketSession?, message: String?)

    /**
     * 发送消息
     *
     * @param session 当前会话
     * @param message 要发送的消息
     * @throws IOException 发送io异常
     */
    @Throws(IOException::class)
    fun sendMessage(session: WebSocketSession?, message: String?)

    /**
     * 发送消息
     *
     * @param userId  用户id
     * @param message 要发送的消息
     * @throws IOException 发送io异常
     */
    @Throws(IOException::class)
    fun sendMessage(userId: String?, message: TextMessage?)

    /**
     * 发送消息
     *
     * @param userId  用户id
     * @param message 要发送的消息
     * @throws IOException 发送io异常
     */
    @Throws(IOException::class)
    fun sendMessage(userId: String?, message: String?)

    /**
     * 发送消息
     *
     * @param session 当前会话
     * @param message 要发送的消息
     * @throws IOException 发送io异常
     */
    @Throws(IOException::class)
    fun sendMessage(session: WebSocketSession?, message: TextMessage?)

    /**
     * 广播
     *
     * @param message 字符串消息
     * @throws IOException 异常
     */
    @Throws(IOException::class)
    fun broadCast(message: String?)

    /**
     * 广播
     *
     * @param message 文本消息
     * @throws IOException 异常
     */
    @Throws(IOException::class)
    fun broadCast(message: TextMessage?)

    /**
     * 处理会话异常
     *
     * @param session 会话
     * @param error   异常
     */
    fun handleError(session: WebSocketSession?, error: Throwable?)

    fun getSessions(): MutableSet<WebSocketSession>
    fun getConnectionCount(): Int
}
