package com.samkelsey.sortersocket.service.sorter;

import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.List;

public class BubbleSorterImpl extends Sorter {

    public BubbleSorterImpl(SimpMessageSendingOperations simpMessageSendingOperations) {
        super(simpMessageSendingOperations);
    }

    @Override
    public List<Integer> sort(List<Integer> unsortedList) throws InterruptedException {
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
                    Thread.sleep(getSortingSpeed() * 100L);
                }
            }
        }
        return sortedList;
    }
}
