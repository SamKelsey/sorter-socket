package com.samkelsey.sortersocket.service;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.sorter.BubbleSorterImpl;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class SorterFactory {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private Sorter sorter;

    public SorterFactory(SimpMessageSendingOperations simpMessageSendingOperations) {
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    public Sorter getSorter(SorterRequestDto sorterRequestDto) {
        sorter = new BubbleSorterImpl(simpMessageSendingOperations);

        if (sorterRequestDto.getSortingSpeed() != null) {
            sorter.setSortingSpeed(sorterRequestDto.getSortingSpeed());
        }

        return sorter;
    }
}
