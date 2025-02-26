package com.tumult.mclu.client.gui.frame.core.geometry;

public interface IDimension {
    float width();
    float height();

    void setWH(float w, float h);

    default void getWH(float[] out) {
        out[0] = width();
        out[1] = height();
    }
}
