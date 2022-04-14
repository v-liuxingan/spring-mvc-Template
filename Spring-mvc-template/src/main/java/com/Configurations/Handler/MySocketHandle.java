package com.Configurations.Handler;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * WebSocket处理类
 * 用以实现具体逻辑
 */
@RequestMapping
public class MySocketHandle extends TextWebSocketHandler {

    /*
    在处理一次普通访问后，才能开启双工通讯
    */
    @RequestMapping("/home")
    public String open() {
        return "socket-template";//视图
    }

/*下面是常用的websocket中的方法*/
    //建立连接
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    //处理文本消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(message.getPayload());
    }

    //ping-pong心跳消息中的pong消息
    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    //异常处理
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        exception.printStackTrace();
    }

    //关闭连接
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

}
