package com.samkelsey.sortersocket.service.sorter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuickSorterImpl extends Sorter {

    Logger logger = LoggerFactory.getLogger(QuickSorterImpl.class);

    public QuickSorterImpl(SimpMessageSendingOperations simpMessageSendingOperations) {
        super(simpMessageSendingOperations, "Quicksort");
    }

    @Override
    public List<Integer> sort(List<Integer> unsortedList) throws InterruptedException {
        logger.info("Quick sorting...");
        return null;
    }
}
