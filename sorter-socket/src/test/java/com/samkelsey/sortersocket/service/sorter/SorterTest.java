package com.samkelsey.sortersocket.service.sorter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SorterTest {

    @Test
    abstract void shouldSortCorrectly_whenValidList() throws InterruptedException;

    protected List<List<Integer>> createValidLists() {
        List<List<Integer>> list = new ArrayList<>();
        list.add(new ArrayList<Integer>(Arrays.asList(5, 3, 6, 8, 8, 10)));
        list.add(new ArrayList<Integer>(Arrays.asList(3, 5, 6, 8, 8, 10)));
        return list;
    }
}
