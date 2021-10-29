package com.samkelsey.sortersocket.service;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;

public abstract class BaseSorter implements Sorter {

    protected final SimpMessageSendingOperations simpMessageSendingOperations;

    public BaseSorter(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    protected void send(ArrayList<Integer> list) {
        simpMessageSendingOperations.convertAndSend("/sorting", list);
    }
}
