package com.samkelsey.sortersocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(MyStompSessionHandler.class);

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("RECEIVED MESSAGE");
        logger.info(payload.toString());
        super.handleFrame(headers, payload);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/sorting", this);
        session.subscribe("/errors", this);
        logger.info("Client connected");
        super.afterConnected(session, connectedHeaders);
    }
}
