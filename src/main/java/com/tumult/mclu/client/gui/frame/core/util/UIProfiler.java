package com.tumult.mclu.client.gui.frame.core.util;

import java.util.HashMap;
import java.util.Map;

public class UIProfiler {
    private static final Map<String, Long> startTimes = new HashMap<>();
    private static final Map<String, Long> totalTimes = new HashMap<>();
    private static final Map<String, Integer> counts = new HashMap<>();

    public static void start(String section) {
        startTimes.put(section, System.nanoTime());
    }

    public static void end(String section) {
        Long start = startTimes.get(section);
        if (start != null) {
            long duration = System.nanoTime() - start;
            totalTimes.put(section, totalTimes.getOrDefault(section, 0L) + duration);
            counts.put(section, counts.getOrDefault(section, 0) + 1);
        }
    }

    public static void printResults() {
        System.out.println("===== UI PROFILING RESULTS =====");
        for (String section : totalTimes.keySet()) {
            long total = totalTimes.get(section);
            int count = counts.get(section);
            double avgMs = (total / count) / 1_000_000.0;
            System.out.printf("%s: %.3f ms avg (%d calls)\n", section, avgMs, count);
        }
    }

    public static void reset() {
        startTimes.clear();
        totalTimes.clear();
        counts.clear();
    }
}