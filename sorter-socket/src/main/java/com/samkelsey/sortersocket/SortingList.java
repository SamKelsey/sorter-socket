package com.samkelsey.sortersocket;

import java.awt.*;

public class SortingList extends List {
    public final List unsortedList;

    public SortingList(List unsortedList) {
        this.unsortedList = unsortedList;
    }
}
