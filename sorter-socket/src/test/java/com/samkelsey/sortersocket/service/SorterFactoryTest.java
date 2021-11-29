package com.samkelsey.sortersocket.service;

import com.samkelsey.sortersocket.TestUtils;
import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.exception.BadRequestException;
import com.samkelsey.sortersocket.service.sorter.BubbleSorterImpl;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SorterFactoryTest {

    @Test
    void shouldReturnDefaultSorter_whenNoConfigProvided() {
        SorterRequestDto req = TestUtils.createSorterRequestDto();

        SorterFactory factory = createFactoryWithRealSorter();
        Sorter sorter = factory.getSorter(req);

        assertEquals(5, sorter.getSortingSpeed());
    }

    @Test
    void shouldReturnCustomSorter_whenConfigProvided() {
        SorterRequestDto req = TestUtils.createSorterRequestDto();
        req.setSortingSpeed(4);

        SorterFactory factory = createFactoryWithRealSorter();
        Sorter sorter = factory.getSorter(req);

        assertEquals(4, sorter.getSortingSpeed());
    }

    @Test
    void shouldThrowBadRequest_whenInvalidSorterMethod() {
        SorterFactory factory = createFactoryWithMockSorter();

        SorterRequestDto req = TestUtils.createSorterRequestDto();
        req.setSortingMethod("Invalid method");

        assertThrows(BadRequestException.class, () -> {
            factory.getSorter(req);
        });
    }

    private SorterFactory createFactoryWithRealSorter() {
        SimpMessageSendingOperations simpMessageSendingOperations = mock(SimpMessageSendingOperations.class);
        Sorter bubbleSorter = new BubbleSorterImpl(simpMessageSendingOperations);
        List<Sorter> sorterList = new ArrayList<>();
        sorterList.add(bubbleSorter);

        return new SorterFactory(sorterList);
    }

    private SorterFactory createFactoryWithMockSorter() {
        Sorter mockBubbleSorter = mock(BubbleSorterImpl.class);
        when(mockBubbleSorter.getSortingMethod()).thenReturn("Bubblesort");
        List<Sorter> sorterList = new ArrayList<>();
        sorterList.add(mockBubbleSorter);

        return new SorterFactory(sorterList);
    }
}
