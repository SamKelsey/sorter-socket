package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class SorterControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    Logger logger = LoggerFactory.getLogger(SorterControllerIntegrationTest.class);

    @Test
    void shouldReturnOK_whenValidPayload() throws Exception {
        WebSocketStompClient stompClient = getStompClient();
        StompSessionHandler sessionHandler = new MyStompSessionHandler();

        StompSession session = stompClient.connect(
                String.format("ws://localhost:%d/socket", port),
                sessionHandler
        ).get();

        Thread.sleep(1000);
        session.send("/app/sort", TestUtils.createSorterRequestDto());

//        JSONObject json = new JSONObject();
//        try {
//            json.put("sorting-list", new int[]{1, 3, 2, 5});
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }

//        Thread.sleep(1000);

//        session.send("/app/sort", new int[]{1, 3, 2, 5});

    }

    private WebSocketStompClient getStompClient() {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient;
    }
}
