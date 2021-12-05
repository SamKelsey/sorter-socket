package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.service.SorterFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SorterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SorterFactory sorterFactory;

    @Test
    void shouldReturnAllSorters_whenGetSorterMethods() throws Exception {
        mockMvc.perform(get("/sorter-methods"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'sorting-methods': ['Quicksort', 'Bubblesort']}"));
    }
}
