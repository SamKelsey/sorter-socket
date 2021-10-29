package com.samkelsey.sortersocket.service;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class SorterFactory {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public SorterFactory(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    public Sorter getSorter(String sorterName) {
        if (sorterName.equals("bubbleSort")) {
            return new BubbleSorterImpl(simpMessageSendingOperations);
        } else {
            // Default to returning this method for now.
            return new BubbleSorterImpl(simpMessageSendingOperations);
        }
    }
}
