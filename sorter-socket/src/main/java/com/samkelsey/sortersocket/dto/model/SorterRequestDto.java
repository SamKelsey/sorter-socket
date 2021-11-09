package com.samkelsey.sortersocket.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SorterRequestDto {

    @NonNull
    @JsonProperty("sorting-list")
    private List<Integer> sortingList;

    @JsonProperty("sorting-method")
    private String sortingMethod;

    @JsonProperty("sorting-speed")
    private Integer sortingSpeed;
}
