package com.samkelsey.sortersocket.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BubbleSortTest extends SortingServiceTest{

    @Mock
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @InjectMocks
    private BubbleSorterImpl bubbleSorterImpl;

    @Test
    void sortCorrectly_whenValidList() throws InterruptedException {
        ArrayList<Integer> unsortedList = createValidList();
        ArrayList<Integer> expectedResult = new ArrayList<>(unsortedList);
        Collections.sort(expectedResult);
        ArrayList<Integer> result = bubbleSorterImpl.sort(unsortedList);

        assertEquals(result, expectedResult);
    }
}
