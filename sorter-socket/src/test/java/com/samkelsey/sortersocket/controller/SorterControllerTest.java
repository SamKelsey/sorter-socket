package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.service.SorterFactory;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SorterControllerTest {

    @Mock
    private SorterFactory sorterFactory;

    @Mock
    private Sorter mockSorter;

    @InjectMocks
    private SorterController sorterController;

//    @Test
//    void shouldReturnOK_whenValidPayload() throws Exception {
//        when(sorterFactory.getSorter(any())).thenReturn(mockSorter);
//
//        SorterRequestDto payload = TestUtils.createSorterRequestDto();
//        ResponseEntity<String> response = sorterController.sort(payload);
//
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//    }
}
