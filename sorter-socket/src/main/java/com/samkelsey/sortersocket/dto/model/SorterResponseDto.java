package com.samkelsey.sortersocket.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class SorterResponseDto {

    @NonNull
    @JsonProperty("evaluated-indexes")
    private Integer[] evaluatedIndexes;

    @JsonProperty("sorting-list")
    private List<Integer> sortingList;

}
