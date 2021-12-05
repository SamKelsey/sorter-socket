package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.dto.model.AllSortersResponseDto;
import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.SorterFactory;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Controller
public class SorterController {

    private final SorterFactory sorterFactory;
    private final Logger logger = LoggerFactory.getLogger(SorterController.class);

    public SorterController(SorterFactory sorterFactory) {
        this.sorterFactory = sorterFactory;
    }

    @GetMapping("/sorter-methods")
    public ResponseEntity<AllSortersResponseDto> getSorterMethods() {
        logger.info("Fetching all sorting methods.");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new AllSortersResponseDto(sorterFactory.getAllSorters()));
    }

    @MessageMapping("/sort")
    public void sort(@Valid @RequestBody SorterRequestDto sorterRequestDto) throws Exception {
        logger.info("Processing new submission.");
        Sorter sorter = sorterFactory.getSorter(sorterRequestDto);
        sorter.sort(sorterRequestDto.getSortingList());
    }

}
