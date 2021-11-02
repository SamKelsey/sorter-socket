package com.samkelsey.sortersocket.service;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.sorter.BubbleSorterImpl;
import com.samkelsey.sortersocket.service.sorter.QuickSorterImpl;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SorterFactoryTest {

    @Autowired
    private SorterFactory sorterFactory;

    @Test
    void shouldReturnDefaultSorter_whenNoConfigProvided() {
        SorterRequestDto req = new SorterRequestDto(Arrays.asList(1, 3));
        Sorter sorter = sorterFactory.getSorter(req);

        assertTrue(sorter instanceof BubbleSorterImpl);
        assertEquals(sorter.getSortingSpeed(), 5);
    }

    @Test
    void shouldReturnCustomSorter_whenConfigProvided() {
        SorterRequestDto req = new SorterRequestDto(Arrays.asList(1, 3));
        req.setSortingSpeed(4);
        req.setSortingMethod("Quicksort");

        Sorter sorter = sorterFactory.getSorter(req);

        assertTrue(sorter instanceof QuickSorterImpl);
        assertEquals(sorter.getSortingSpeed(), 4);
    }

}
