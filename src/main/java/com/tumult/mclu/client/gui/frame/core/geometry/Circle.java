package com.tumult.mclu.client.gui.frame.core.geometry;

public class Circle extends RoundRect implements IRect {
    public Circle(float x, float y, float z, float r) {
        super(x, y, r * 2f, r * 2f, z, r);
    }
    Circle(float[] in) {
        this(in[0], in[1], in[2], in[3]);
    }
    @Override public void copyTo(float[] out){
        out[0] = left();
        out[1] = top();
        out[2] = depth();
        out[3] = radius();
    }
    @Override
    public boolean contains(float x, float y) {
        return squareDistanceToCenter(x, y) <= radius() * radius();
    }

    public float squareDistanceToCenter(float x, float y) {
        float dx = x - centerX();
        float dy = y - centerY();
        return dx * dx + dy * dy;
    }
}
