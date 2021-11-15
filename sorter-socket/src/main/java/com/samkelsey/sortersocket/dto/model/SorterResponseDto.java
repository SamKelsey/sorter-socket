package com.samkelsey.sortersocket.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class SorterResponseDto {

    @NonNull
    @JsonProperty("sorting-list")
    private List<Integer> sortingList;

    @JsonProperty("current-indexes")
    private int[] currentIndexes;
}
