package com.samkelsey.sortersocket.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SorterRequestDto {

    @JsonProperty("sorting-list")
    public ArrayList<Integer> sortingList;

    @JsonProperty("sorting-method")
    public String sortingMethod;
}
