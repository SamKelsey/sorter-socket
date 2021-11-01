package com.samkelsey.sortersocket.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class SorterRequestDto {

    @NotNull
    @JsonProperty("sorting-list")
    private List<Integer> sortingList;

    @JsonProperty("sorting-method")
    private String sortingMethod;

    @JsonProperty("sorting-speed")
    private Integer sortingSpeed;
}
