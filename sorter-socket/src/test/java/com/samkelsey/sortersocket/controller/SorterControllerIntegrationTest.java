package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.TestUtils;
import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class SorterControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;
    private WebSocketStompClient stompClient;
    private Stack<List<Integer>> receivedMessages = new Stack<>();
    private final String SOCKET_ENDPOINT = "ws://localhost:%d/socket";

    Logger logger = LoggerFactory.getLogger(SorterControllerIntegrationTest.class);

    @BeforeEach
    void init() {
        WebSocketClient client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        receivedMessages.clear();
    }

    @Test
    void shouldReturnSortedList_whenValidPayload() throws Exception {
        StompSession session = stompClient.connect(
                String.format(SOCKET_ENDPOINT, port),
                new MyStompSessionHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return SorterRequestDto.class;
                    }
                }
        ).get();

        session.send("/app/sort", TestUtils.createSorterRequestDto());
        Thread.sleep(2000);

        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 3, 8));
        assertEquals(expected, receivedMessages.pop());

        session.disconnect();
    }

    @Test
    void shouldReturnException_whenInvalidPayload() {
        // TODO
    }

    private class MyStompSessionHandler extends StompSessionHandlerAdapter {

        Logger logger = LoggerFactory.getLogger(MyStompSessionHandler.class);

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            SorterRequestDto msg = (SorterRequestDto) payload;
            receivedMessages.push(msg.getSortingList());
            logger.info(String.format("Sorting list status: %s", msg.getSortingList().toString()));
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
}
