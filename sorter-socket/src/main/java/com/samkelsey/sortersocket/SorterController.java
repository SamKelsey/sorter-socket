package com.samkelsey.sortersocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Objects;

@Controller
public class SorterController {
    private ArrayList<Integer> WORKING_LIST;

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    public SorterController(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    @MessageMapping("/sort")
    @SendTo("/sorting")
    public void sort(ArrayList<Integer> message) throws Exception {

        if (Objects.nonNull(message)) {
            WORKING_LIST = message;
        }

        sortAndSend();
    }

    // BubbleSort the list and send a response for each iteration.
    private void sortAndSend() throws InterruptedException {
        int counter = 1;
        while (counter > 0){
            counter = 0;
            for (int i = 0; i < WORKING_LIST.size() - 1; i++) {
                if (WORKING_LIST.get(i) > WORKING_LIST.get(i + 1)) {
                    int placeholder = WORKING_LIST.get(i);
                    WORKING_LIST.set(i, WORKING_LIST.get(i + 1));
                    WORKING_LIST.set(i + 1, placeholder);
                    simpMessageSendingOperations.convertAndSend("/sorting", WORKING_LIST);
                    counter++;
                    Thread.sleep(500);
                }
            }
        }
    }
}
