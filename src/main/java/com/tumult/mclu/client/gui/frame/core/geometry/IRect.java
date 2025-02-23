package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public interface IRect {
    int X = 0;
    int Y = 1;
    int W = 2;
    int H = 3;
    int Z = 4;
    int R = 5;

    float left();
    float top();
    float width();
    float height();
    float zLevel();
    int res();
    VertexFormat.Mode mode();

    void setUL(float x, float y);
    void setWH(float w, float h);
    void setBR(float b, float r);
    void setZ(float zLevel);

    void copyTo(float[] out);
    default void normalize(float divisor, float[] out) {
        float invDiv = 1.0f / divisor;
        out[0] = left() * invDiv;
        out[1] = top() * invDiv;
        out[2] = right() * invDiv;
        out[3] = bottom() * invDiv;
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
    default boolean contains(float x, float y) {
        return x >= left() && y >= top() && x <= right() && y <= bottom();
    }
    default float right() {return left() + width();}
    default float bottom() {return top() + height();}
    default float centerX() {return left() + (width() * 0.5f);}
    default float centerY() {return top() + (height() * 0.5f);}

    default void setUL(float[] v) {setUL(v[0], v[1]);}
    default void setBR(float[] v) {setBR(v[0], v[1]);}

    default void setCenter(float[] v) {setCenter(v[0], v[1]);}
    default void moveBy(float[] v) {moveBy(v[0], v[1]);}
    default void dragTo(float[] v) {setUL(v[0] - left(), v[1] - top());}
    default boolean contains(float[] v) {return contains(v[0], v[1]);}
}
