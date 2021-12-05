package com.samkelsey.sortersocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samkelsey.sortersocket.TestUtils;
import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
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
public class WebsocketIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    private StompSession session;

    private final Stack<List<Integer>> receivedMessages = new Stack<>();
    private final Stack<ResponseEntity<String>> receivedErrors = new Stack<>();

    private final String SORTER_ENDPOINT = "/app/sort";
    private final int THREAD_SLEEP_DURATION = 2000;

    @BeforeEach
    void init() throws Exception {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String SOCKET_ENDPOINT = String.format("ws://localhost:%d/socket", port);
        session = stompClient.connect(
                SOCKET_ENDPOINT,
                new MyStompSessionHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        return Object.class;
                    }
                }
        ).get();

        receivedMessages.clear();
        receivedErrors.clear();
    }

    @Test
    void shouldReturnSortedList_whenValidPayload() throws Exception {
        session.send(SORTER_ENDPOINT, TestUtils.createSorterRequestDto());
        Thread.sleep(THREAD_SLEEP_DURATION);

        List<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 3, 8));
        assertEquals(expected, receivedMessages.pop());
    }

    @Test
    void shouldReturnError_whenMissingRequiredField() throws Exception {
        session.send(SORTER_ENDPOINT, TestUtils.createInvalidSorterRequestDto());
        Thread.sleep(THREAD_SLEEP_DURATION);

        ResponseEntity<String> result = receivedErrors.pop();
        ResponseEntity<String> expected = ResponseEntity
                .status(400)
                .body("Bad request: sorting-list cannot be null.");

        assertEquals(expected.getStatusCode(), result.getStatusCode());
        assertEquals(expected.getBody(), result.getBody());
    }

    @Test
    void shouldReturnBadRequestError_whenInvalidSorterMethod() throws Exception {
        SorterRequestDto dto = new SorterRequestDto();
        dto.setSortingList(new ArrayList<>(Arrays.asList(1, 3, 2, 5)));
        dto.setSortingMethod("fail");

        session.send(SORTER_ENDPOINT, dto);
        Thread.sleep(THREAD_SLEEP_DURATION);

        ResponseEntity<String> result = receivedErrors.pop();
        ResponseEntity<String> expected = ResponseEntity
                .status(400)
                .body("Bad request: 'fail' sorting-method does not exist.");

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
