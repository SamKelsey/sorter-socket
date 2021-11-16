package com.samkelsey.sortersocket.service.sorter;

import com.samkelsey.sortersocket.dto.model.SorterResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BubbleSorterImpl extends Sorter {

    Logger logger = LoggerFactory.getLogger(BubbleSorterImpl.class);

    public BubbleSorterImpl(SimpMessageSendingOperations simpMessageSendingOperations) {
        super(simpMessageSendingOperations, "Bubblesort");
    }

    @Override
    public List<Integer> sort(List<Integer> unsortedList) throws InterruptedException {
        logger.info("Beginning Bubblesort");
        int counter = 1;
        List<Integer> sortedList = new ArrayList<>(unsortedList);
        while (counter > 0){
            counter = 0;
            for (int i = 0; i < sortedList.size() - 1; i++) {
                if (sortedList.get(i) > sortedList.get(i + 1)) {
                    swap(i, i + 1, sortedList);
                    send(new SorterResponseDto(sortedList, new int[]{i, i + 1}));
                    counter++;
                    Thread.sleep(getSortingSpeed() * 100L);
                }
            }
        }
        return sortedList;
    }

    /**
     * Performs an inplace swap of two elements in a list, given their indexes.
     * @param i Index of first element to swap.
     * @param j Index of second element to swap.
     * @param arr List to perform swap on.
     */
    private void swap(int i, int j, List<Integer> arr) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }
}
