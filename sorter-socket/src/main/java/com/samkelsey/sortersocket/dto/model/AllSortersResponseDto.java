package com.samkelsey.sortersocket.dto.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
@Data
public class AllSortersResponseDto {

    @JsonProperty("sorter-methods")
    @NonNull
    private Collection<String> sorterMethods;

}
