package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public interface IRect extends IPosition, IDimension {

    default float right() {
        return left() + width();
    }

    default float bottom() {
        return top() + height();
    }

    default float centerX() {
        return left() + (width() * 0.5f);
    }

    default float centerY() {
        return top() + (height() * 0.5f);
    }

    default void setBR(float b, float r) {
        setUL(b - width(), r - height());
    }

    default void setBR(float[] v) {
        setBR(v[0], v[1]);
    }

    default void setCenter(float x, float y) {
        setUL(x - (width() * 0.5f), y - (height() * 0.5f));
    }

    default void setCenter(float[] v) {
        setCenter(v[0], v[1]);
    }

    default void getBR(float[] out) {
        out[0] = right();
        out[1] = bottom();
    }

    default void center(float[] out) {
        out[0] = centerX();
        out[1] = centerY();
    }

    default boolean contains(float x, float y) {
        return x >= left() && y >= top() && x <= right() && y <= bottom();
    }

    default boolean contains(float[] v) {
        return contains(v[0], v[1]);
    }
}
