package com.samkelsey.sortersocket.service.sorter;

import com.samkelsey.sortersocket.dto.model.SorterResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.List;

public abstract class Sorter {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final String sortingMethod;
    private int sortingSpeed = 5;

    public Sorter(SimpMessageSendingOperations simpMessageSendingOperations, String sortingMethod) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.sortingMethod = sortingMethod;
    }

    public abstract List<Integer> sort(List<Integer> unsortedList) throws InterruptedException;

    protected void send(SorterResponseDto response) {
        simpMessageSendingOperations.convertAndSend("/sorting", ResponseEntity.status(HttpStatus.OK).body(response));
    }

    /**
     * Performs an in-place swap of two elements in a list, given their indexes.
     * @param i Index of first element to swap.
     * @param j Index of second element to swap.
     * @param arr List to perform swap on.
     */
    protected void swap(int i, int j, List<Integer> arr) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
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
