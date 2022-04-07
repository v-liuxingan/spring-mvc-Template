package com.Configurations.Handler;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RequestMapping
public class MySocketHandle extends TextWebSocketHandler {
}
