package com.samkelsey.sortersocket.service.sorter;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuickSorterImpl extends Sorter {

    public QuickSorterImpl(SimpMessageSendingOperations simpMessageSendingOperations) {
        super(simpMessageSendingOperations, "Quicksort");
    }

    @Override
    public List<Integer> sort(List<Integer> unsortedList) throws InterruptedException {
        System.out.println("Quick sorting...");
        return null;
    }
}
