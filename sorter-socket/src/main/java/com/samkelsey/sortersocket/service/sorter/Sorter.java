package com.samkelsey.sortersocket.service.sorter;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.List;

public abstract class Sorter {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private int sortingSpeed = 5;
    private String sortingMethod;

    public Sorter(SimpMessageSendingOperations simpMessageSendingOperations, String sortingMethod) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.sortingMethod = sortingMethod;
    }


    public abstract List<Integer> sort(List<Integer> unsortedList) throws InterruptedException;

    protected void send(ArrayList<Integer> list) {
        simpMessageSendingOperations.convertAndSend("/sorting", list);
    }

    public void setSortingSpeed(int sortingSpeed) {
        this.sortingSpeed = sortingSpeed;
    }

    public int getSortingSpeed() {
        return sortingSpeed;
    }

    public String getSortingMethod() {
        return sortingMethod;
    }

}
