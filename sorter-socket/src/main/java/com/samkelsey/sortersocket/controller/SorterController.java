package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.SorterFactory;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

@Controller
public class SorterController {

    private final SorterFactory sorterFactory;

    public SorterController(SorterFactory sorterFactory) {
        this.sorterFactory = sorterFactory;
    }

    @MessageMapping("/sort")
    public ResponseEntity<String> sort(@Valid SorterRequestDto sorterRequestDto) throws Exception {

        Sorter sorter = sorterFactory.getSorter(sorterRequestDto);
        sorter.sort(sorterRequestDto.getSortingList());

        return new ResponseEntity<>("Sort request received.", HttpStatus.OK);
    }
}
