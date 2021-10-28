package com.samkelsey.sortersocket.service;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BubbleSort extends SortingService{

    public BubbleSort(SimpMessageSendingOperations simpMessageSendingOperations) {
        super(simpMessageSendingOperations);
    }

    @Override
    public ArrayList<Integer> sort(ArrayList<Integer> unsortedList) throws InterruptedException {
        int counter = 1;
        ArrayList<Integer> sortedList = new ArrayList<>(unsortedList);
        while (counter > 0){
            counter = 0;
            for (int i = 0; i < sortedList.size() - 1; i++) {
                if (sortedList.get(i) > sortedList.get(i + 1)) {
                    int placeholder = sortedList.get(i);
                    sortedList.set(i, sortedList.get(i + 1));
                    sortedList.set(i + 1, placeholder);
                    send(sortedList);
                    counter++;
                    Thread.sleep(500);
                }
            }
        }
        return sortedList;
    }
}
