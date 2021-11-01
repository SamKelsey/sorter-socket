package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.SorterFactory;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SorterControllerTest {

    @Mock
    private SorterFactory sorterFactory;

    @Mock
    private Sorter mockSorter;

    @InjectMocks
    private SorterController sorterController;

    @Test
    void shouldReturnOK_whenValidPayload() throws Exception {
        when(sorterFactory.getSorter(any())).thenReturn(mockSorter);

        SorterRequestDto payload = getValidPayload();
        ResponseEntity<String> response = sorterController.sort(payload);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    private SorterRequestDto getValidPayload() {
        return new SorterRequestDto(Arrays.asList(3, 1, 5, 3));
    }
}
