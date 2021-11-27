package com.samkelsey.sortersocket.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SorterRequestDto {

    @NotNull(message = "sorting-list cannot be null.")
    @JsonProperty("sorting-list")
    private List<Integer> sortingList;

    @NotNull(message = "sorting-method cannot be null.")
    @JsonProperty("sorting-method")
    private String sortingMethod;

    @JsonProperty("sorting-speed")
    private Integer sortingSpeed;
}
