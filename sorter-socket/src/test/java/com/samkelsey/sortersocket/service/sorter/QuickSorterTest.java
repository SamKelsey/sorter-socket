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
public class QuickSorterTest extends SorterTest {

    @Mock
    SimpMessageSendingOperations simpMessageSendingOperations;

    @InjectMocks
    QuickSorterImpl sorter;

    @Test
    void shouldSortCorrectly_whenValidList() throws InterruptedException {
        List<List<Integer>> lists = createValidLists();
        List<Integer> sortedList = sorter.sort(lists.get(0));
        assertEquals(sortedList, lists.get(1));
    }
}
