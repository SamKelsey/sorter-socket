package com.samkelsey.sortersocket.service;

import com.samkelsey.sortersocket.dto.model.SorterRequestDto;
import com.samkelsey.sortersocket.exception.BadRequestException;
import com.samkelsey.sortersocket.service.sorter.Sorter;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SorterFactory {

    private final Map<String, Sorter> sorterServicesByMethodName;

    public SorterFactory(List<Sorter> sorterServices) {
        this.sorterServicesByMethodName = sorterServices.stream()
                .collect(Collectors.toMap(Sorter::getSortingMethod, Function.identity()));
    }

    public Collection<String> getAllSorters() {
        return sorterServicesByMethodName.keySet();
    }

    public Sorter getSorter(SorterRequestDto sorterRequestDto) {
        Sorter sorter = sorterServicesByMethodName.get(sorterRequestDto.getSortingMethod());
        if (Objects.isNull(sorter)) {
            throw new BadRequestException(
                    String.format(
                            "'%s' sorting-method does not exist.",
                            sorterRequestDto.getSortingMethod()
                    )
            );
        }

        if (sorterRequestDto.getSortingSpeed() != null) {
            sorter.setSortingSpeed(sorterRequestDto.getSortingSpeed());
        }

        return sorter;
    }
}
