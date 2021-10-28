package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.BubbleSort;
import com.samkelsey.sortersocket.service.SortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Objects;

@Controller
public class SorterController {

    private final SortingService sortingService;

    @Autowired
    public SorterController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @MessageMapping("/sort")
    public void sort(SorterRequestDto sorterRequestDto) throws Exception {

        if (Objects.nonNull(sorterRequestDto)) {
           sortingService.sort(sorterRequestDto.getSortingList());
        }
    }
}
