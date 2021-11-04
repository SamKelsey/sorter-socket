package com.samkelsey.sortersocket.service;

import com.samkelsey.sortersocket.TestUtils;
import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.sorter.BubbleSorterImpl;
import com.samkelsey.sortersocket.service.sorter.QuickSorterImpl;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SorterFactoryTest {

    @Mock
    QuickSorterImpl mockQuickSorter;

    @Mock
    BubbleSorterImpl mockBubbleSorter;

    @Mock
    SimpMessageSendingOperations simpMessageSendingOperations;


    @Test
    void shouldReturnDefaultSorter_whenNoConfigProvided() {
        when(mockQuickSorter.getSortingMethod()).thenReturn("Quicksort");
        when(mockBubbleSorter.getSortingMethod()).thenReturn("Bubblesort");
        List<Sorter> sorterList = new ArrayList<>();
        sorterList.add(mockBubbleSorter);
        sorterList.add(mockQuickSorter);

        SorterFactory factory = new SorterFactory(sorterList);

        SorterRequestDto req = TestUtils.createSorterRequestDto();

        Sorter sorter = factory.getSorter(req);

        assertEquals(sorter, mockBubbleSorter);
    }

    @Test
    void shouldReturnCustomSorter_whenConfigProvided() {
        when(mockBubbleSorter.getSortingMethod()).thenReturn("Bubblesort");

        QuickSorterImpl quickSorter = new QuickSorterImpl(simpMessageSendingOperations);

        List<Sorter> sorterList = new ArrayList<>();
        sorterList.add(mockBubbleSorter);
        sorterList.add(quickSorter);

        SorterFactory factory = new SorterFactory(sorterList);

        SorterRequestDto req = TestUtils.createSorterRequestDto();
        int sortingSpeed = 4;
        req.setSortingSpeed(sortingSpeed);
        req.setSortingMethod("Quicksort");

        Sorter sorter = factory.getSorter(req);

        assertEquals(sorter, quickSorter);
        assertEquals(sorter.getSortingSpeed(), sortingSpeed);
    }

}
