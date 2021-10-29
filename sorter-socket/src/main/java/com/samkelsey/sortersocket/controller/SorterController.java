package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.BaseSorter;
import com.samkelsey.sortersocket.service.BubbleSorterImpl;
import com.samkelsey.sortersocket.service.Sorter;
import com.samkelsey.sortersocket.service.SorterFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class SorterController {

    private final SorterFactory sorterFactory;

    public SorterController(SorterFactory sorterFactory) {
        this.sorterFactory = sorterFactory;
    }

    @MessageMapping("/sort")
    public void sort(SorterRequestDto sorterRequestDto) throws Exception {

        if (Objects.nonNull(sorterRequestDto)) {
            Sorter sorter = sorterFactory.getSorter(sorterRequestDto.getSortingMethod());
            sorter.sort(sorterRequestDto.getSortingList());
        }
    }
}
