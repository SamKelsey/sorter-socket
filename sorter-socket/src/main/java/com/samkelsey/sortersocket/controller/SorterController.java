package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.SorterFactory;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Controller
public class SorterController {

    private final SorterFactory sorterFactory;
    private final Logger logger = LoggerFactory.getLogger(SorterController.class);

    public SorterController(SorterFactory sorterFactory) {
        this.sorterFactory = sorterFactory;
    }

    @MessageMapping("/sort")
    public void sort(@Valid @RequestBody SorterRequestDto sorterRequestDto) throws Exception {
        logger.info("Passed validation");
        // TODO: Implement new thread to sort and send to subscribers so controller can return success response quickly.
        Sorter sorter = sorterFactory.getSorter(sorterRequestDto);
        sorter.sort(sorterRequestDto.getSortingList());
    }
}
