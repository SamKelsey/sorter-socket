package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.SorterFactory;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SorterController {

    private final SorterFactory sorterFactory;

    public SorterController(SorterFactory sorterFactory) {
        this.sorterFactory = sorterFactory;
    }

    @MessageMapping("/sort")
    public ResponseEntity<String> sort(SorterRequestDto sorterRequestDto) throws Exception {

        // TODO: Implement new thread to sort and send to subscribers so controller can return success response quickly.
        Sorter sorter = sorterFactory.getSorter(sorterRequestDto);
        sorter.sort(sorterRequestDto.getSortingList());

        return new ResponseEntity<>("Sort request received.", HttpStatus.OK);
    }
}
