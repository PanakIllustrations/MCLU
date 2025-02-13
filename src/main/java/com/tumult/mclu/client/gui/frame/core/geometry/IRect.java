package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public interface IRect {
    float left();
    float top();
    float width();
    float height();
    float zLevel();
    int resolution();

    VertexFormat.Mode format();

    void setUL(float x, float y);
    void setWH(float w, float h);
    void setBR(float b, float r);
    void setZ(float zLevel);

    void copy(float[] out);

    default void normalize(float[] out, float divisor) {
        float invDiv = 1.0f / divisor;
        out[0] = left() * invDiv;
        out[1] = top() * invDiv;
        out[2] = width() * invDiv;
        out[3] = height() * invDiv;
        out[4] = zLevel();
    }
    default void getUL(float[] out) {
        out[0] = left();
        out[1] = top();
    }

    default void getWH(float[] out) {
        out[0] = width();
        out[1] = height();
    }

    default void getBR(float[] out) {
        out[0] = right();
        out[1] = bottom();
    }

    default void center(float[] out) {
        out[0] = centerX();
        out[1] = centerY();
    }

    default void setCenter(float x, float y) {
        setUL(x - (width() * 0.5f), y - (height() * 0.5f));
    }
    default void moveBy(float x, float y) {
        setUL(left() + x, top() + y);
    }
    default void dragTo(float x, float y, boolean button) {
        if (!button || !contains(x, y)) return;
        moveBy(x - left(), y - top());
    }
    default boolean contains(float x, float y) {
        return x >= left() && y >= top() && x <= right() && y <= bottom();
    }
    default int len(){return resolution() * 8;}
    default float right() {return left() + width();}
    default float bottom() {return top() + height();}
    default float centerX() {return left() + (width() * 0.5f);}
    default float centerY() {return top() + (height() * 0.5f);}

    default void setUL(float[] v) {setUL(v[0], v[1]);}
    default void setBR(float[] v) {setBR(v[0], v[1]);}
    default void setWH(float[] v) {setWH(v[0], v[1]);}

    default void setCenter(float[] v) {setCenter(v[0], v[1]);}
    default void moveBy(float[] v) {moveBy(v[0], v[1]);}
    default void dragTo(float[] v, boolean button) {dragTo(v[0], v[1], button);}
    default boolean contains(float[] v) {return contains(v[0], v[1]);}

}
