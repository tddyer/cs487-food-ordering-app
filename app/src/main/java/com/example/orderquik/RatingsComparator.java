package com.example.orderquik;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class RatingsComparator implements Comparator<HashMap<String, Integer>> {


    @Override
    public int compare(HashMap<String, Integer> t0, HashMap<String, Integer> t1) {
        int v0 = 0;
        int v1 = 0;
        for (Map.Entry<String, Integer> entry : t0.entrySet())
            v0 = entry.getValue();
        for (Map.Entry<String, Integer> entry : t1.entrySet())
            v1 = entry.getValue();
        return v1 - v0;
    }
}
