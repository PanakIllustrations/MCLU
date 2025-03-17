package com.tumult.mclu.client.gui.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PointArrayPool {
    private static final Map<Integer, Queue<float[]>> POOLS = new HashMap<>();

    public static float[] obtain(int size) {
        Queue<float[]> pool = POOLS.computeIfAbsent(size, k -> new ConcurrentLinkedQueue<>());
        float[] array = pool.poll();
        return array != null ? array : new float[size];
    }

    public static void recycle(float[] array) {
        if (array != null) {
            // Reset array to zero
            for (int i = 0; i < array.length; i++) {
                array[i] = 0;
            }
            Queue<float[]> pool = POOLS.get(array.length);
            if (pool != null && pool.size() < 100) { // Limit pool size
                pool.offer(array);
            }
        }
    }
}