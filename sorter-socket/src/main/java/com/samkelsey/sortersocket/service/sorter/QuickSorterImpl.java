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
        quicksort(0, workingList.size() - 1);
        return workingList;
    }

    /**
     * Recursive method that triggers partitioning of subsection of workingList.
     * @param low Minimum index of workingList to work from.
     * @param high Maximum index of workingList to work from.
     */
    private void quicksort(int low, int high) {
        if (low < high) {
            // Partition the array
            int pi = partition(low, high);

            quicksort(low, pi - 1);
            quicksort(pi + 1, high);
        }
    }

    /**
     * This method is responsible for correctly partitioning a subarray of workingList.
     * The pivot element is put in it's correct location in workingList and it's index is returned.
     * @param low Minimum index for workingList subarray
     * @param high Maxumum working index for workingList subarray
     * @return An int, marking the updated position of the pivot element.
     */
    private int partition(int low, int high) {
        // Take last element as pivot
        int pi = workingList.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (workingList.get(j) < pi) {
                i++;
                swap(i, j);
            }
        }

        i++;
        swap(i, high);

        return i;
    }

    /**
     * Utility method to swap 2 workingList values, given their indexes.
     * @param i index of workingList to swap
     * @param j index of workingList to swap
     */
    private void swap(int i, int j) {
        int swapTemp = workingList.get(i);
        workingList.set(i, workingList.get(j));
        workingList.set(j, swapTemp);
        send(workingList);
    }
}
