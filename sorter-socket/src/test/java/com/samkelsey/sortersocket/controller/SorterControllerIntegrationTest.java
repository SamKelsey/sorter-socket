package com.samkelsey.sortersocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samkelsey.sortersocket.TestUtils;
import com.samkelsey.sortersocket.dto.model.SorterResponseDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * An integration test class that uses a websocket client to post requests to the sorter server endpoint.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class SorterControllerIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private WebSocketStompClient stompClient;
    private StompSession session;

    private final Stack<List<Integer>> receivedMessages = new Stack<>();
    private final Stack<ResponseEntity<String>> receivedErrors = new Stack<>();

    private String SOCKET_ENDPOINT;
    private final String SORTER_ENDPOINT = "/app/sort";

    private final int THREAD_SLEEP_DURATION = 2000;

    @BeforeEach
    void init() {
        SOCKET_ENDPOINT = String.format("ws://localhost:%d/socket", port);

        WebSocketClient client = new StandardWebSocketClient();
        stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        receivedMessages.clear();
        receivedErrors.clear();
    }

    @Test
    void shouldReturnSortedList_whenValidPayload() throws Exception {
        session = stompClient.connect(
                SOCKET_ENDPOINT,
                new MyStompSessionHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return Object.class;
                    }
                }
        ).get();

        session.send(SORTER_ENDPOINT, TestUtils.createSorterRequestDto());
        Thread.sleep(THREAD_SLEEP_DURATION);

        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 3, 8));
        assertEquals(expected, receivedMessages.pop());
    }

    @Test
    void shouldReturnException_whenInvalidPayload() throws Exception {
        session = stompClient.connect(
                SOCKET_ENDPOINT,
                new MyStompSessionHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return Object.class;
                    }
                }
        ).get();

        session.send(SORTER_ENDPOINT, TestUtils.createInvalidSorterRequestDto());
        Thread.sleep(THREAD_SLEEP_DURATION);

        ResponseEntity<String> result = receivedErrors.pop();
        ResponseEntity<String> expected = ResponseEntity
                .status(400)
                .body("Bad request: sorting-list cannot be null.");

        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @AfterEach
    void teardown() {
        session.disconnect();
    }

    private abstract class MyStompSessionHandler extends StompSessionHandlerAdapter {

        Logger logger = LoggerFactory.getLogger(MyStompSessionHandler.class);

        @Override
        public abstract Type getPayloadType(StompHeaders headers);

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            session.subscribe("/sorting", this);
            session.subscribe("/errors", this);
            logger.info("Client connected");
            super.afterConnected(session, connectedHeaders);
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            try {
                String destination = headers.getDestination();
                if (Objects.isNull(destination)) {
                    throw new NullPointerException("No destination specified");
                }

                Object dto = deserializePayload(destination, payload);

                if (destination.equals("/sorting")) {
                    receivedMessages.push(((SorterResponseDto) dto).getSortingList());
                    logger.info(String.format("Sorting list status: %s", ((SorterResponseDto) dto).getSortingList()));
                } else if (destination.equals("/errors")) {
                    receivedErrors.push((ResponseEntity<String>) dto);
                    logger.info(String.format("Error received: %s", dto.toString()));
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            super.handleFrame(headers, payload);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            logger.error(String.format("Error occured: %s", exception));
            super.handleException(session, command, headers, payload, exception);
        }

        private Object deserializePayload(String destination, Object payload) throws JSONException, JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();

            JSONObject json = new JSONObject(
                    new String((byte []) payload,
                    StandardCharsets.UTF_8));

            switch (destination) {
                case "/sorting":
                    JSONObject body = json.getJSONObject("body");
                    return mapper.readValue(body.toString(), SorterResponseDto.class);
                case "/errors":
                    return ResponseEntity
                        .status(json.getInt("statusCodeValue"))
                        .body(json.getString("body"));
                default:
                    return null;
            }
        }
    }
}
