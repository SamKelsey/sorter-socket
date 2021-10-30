package com.samkelsey.sortersocket.controller;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.service.Sorter.Sorter;
import com.samkelsey.sortersocket.service.SorterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class SorterController {

    private final SorterFactory sorterFactory;

    public SorterController(SorterFactory sorterFactory) {
        this.sorterFactory = sorterFactory;
    }

    @MessageMapping("/sort")
    public ResponseEntity<String> sort(SorterRequestDto sorterRequestDto) throws Exception {

        if (Objects.isNull(sorterRequestDto)) {
            return new ResponseEntity<>("Message body cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        Sorter sorter = sorterFactory.getSorter(sorterRequestDto.getSortingMethod());
        sorter.sort(sorterRequestDto.getSortingList());

        return new ResponseEntity<>("Sort request received.", HttpStatus.OK);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }
}
