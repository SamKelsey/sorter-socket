package com.samkelsey.sortersocket.service.sorter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BubbleSortTest extends SorterTest {

    @Mock
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @InjectMocks
    private BubbleSorterImpl bubbleSorterImpl;

    @Test
    void shouldSortCorrectly_whenValidList() throws InterruptedException {
        Sorter sorter = new BubbleSorterImpl(simpMessageSendingOperations);
        List<List<Integer>> lists = createValidLists();
        List<Integer> sortedList = sorter.sort(lists.get(0));
        assertEquals(sortedList, lists.get(1));
    }
}
