package com.samkelsey.sortersocket;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;

import java.util.Arrays;

public class TestUtils {

    public static SorterRequestDto createSorterRequestDto() {
        SorterRequestDto dto = new SorterRequestDto();
        dto.setSortingList(Arrays.asList(1, 3, 2, 8, 3));
        return dto;
    }
}
