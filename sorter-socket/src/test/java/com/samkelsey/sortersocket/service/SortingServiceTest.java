package com.samkelsey.sortersocket.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class SortingServiceTest {

    @Test
    abstract void sortCorrectly_whenValidList() throws InterruptedException;

    protected ArrayList<Integer> createValidList() {
        return new ArrayList<Integer>(Arrays.asList(5, 3, 6, 8, 8, 10));
    }
}
