package com.samkelsey.sortersocket.service.sorter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuickSorterImpl extends Sorter {

    private final Logger logger = LoggerFactory.getLogger(QuickSorterImpl.class);
    private List<Integer> workingList;

    public QuickSorterImpl(SimpMessageSendingOperations simpMessageSendingOperations) {
        super(simpMessageSendingOperations, "Quicksort");
    }

    @Override
    public List<Integer> sort(List<Integer> unsortedList) throws InterruptedException {
        workingList = unsortedList;
        return null;
    }

    /**
     * Recursive method that updates workingList, within a given subsection, high and low.
     **/
    private List<Integer> quicksort(int low, int high) {
        return null;
    }

    /**
     * Returns index of pivot after being put in correct place.
     */
    private int partition(List<Integer> arr) {
        return 1;
    }

    /**
     * Utility method to swap 2 workingList values, given their indexes.
     */
    private void swap(int i, int j) {

    }
}
