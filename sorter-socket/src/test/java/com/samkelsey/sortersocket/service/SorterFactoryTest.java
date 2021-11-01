package com.samkelsey.sortersocket.service;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SorterFactoryTest {

    @Mock
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @InjectMocks
    private SorterFactory sorterFactory;

    @Test
    void shouldReturnDefaultSorter_whenNoConfigProvided() {
        SorterRequestDto req = new SorterRequestDto(Arrays.asList(1, 3));
        Sorter sorter = sorterFactory.getSorter(req);

        assertEquals(sorter.getSortingSpeed(), 5);
    }

    @Test
    void shouldReturnCustomSorter_whenConfigProvided() {
        SorterRequestDto req = new SorterRequestDto(Arrays.asList(1, 3));
        req.setSortingSpeed(4);
        req.setSortingMethod("bubblesort");

        Sorter sorter = sorterFactory.getSorter(req);
        assertEquals(sorter.getSortingSpeed(), 4);
    }

}
