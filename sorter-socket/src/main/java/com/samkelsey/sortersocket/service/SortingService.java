package com.samkelsey.sortersocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;

public abstract class SortingService {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    public SortingService(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    protected void send(ArrayList<Integer> list) {
        simpMessageSendingOperations.convertAndSend("/sorting", list);
    }

    public abstract ArrayList<Integer> sort(ArrayList<Integer> unsortedList) throws InterruptedException;
}
