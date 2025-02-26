package com.tumult.mclu.client.gui.frame.core.geometry;

public interface IPosition {
    float left();
    float top();

    void setUL(float x, float y);
    default void setUL(float[] in){
        setUL(in[0], in[1]);
    }
    default void getUL(float[] out){
        out[0] = left();
        out[1] = top();
    }
    default void moveBy(float x, float y){
        setUL(left() + x, top() + y);
    }
    default void moveBy(float[] in){
        moveBy(in[0], in[1]);
    }
}
