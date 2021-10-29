package com.samkelsey.sortersocket.service;

import java.util.ArrayList;

public interface Sorter {

    ArrayList<Integer> sort(ArrayList<Integer> unsortedList) throws InterruptedException;

}
