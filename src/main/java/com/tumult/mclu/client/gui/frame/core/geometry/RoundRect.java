package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public class RoundRect extends Rect implements IRect {
    private float rx, ry;
    private int res = 10;

    public RoundRect(float x, float y, float w, float h, float z, float rx, float ry) {
        super(x, y, w, h, z);
        this.rx = rx;
        this.ry = ry;
    }
    RoundRect(float[] in) {
        this(in[0], in[1], in[2], in[3], in[4], in[5], in[6]);
    }
    RoundRect(RoundRect st) {
        super(st);
        this.rx = st.rx;
        this.ry = st.ry;
        this.res = st.res;
    }
    public float radiusX() {return rx;}
    public float radiusY() {return ry;}
    public void setRX(float rx) {this.rx = rx;}
    public void setRY(float ry) {this.ry = ry;}
    public void setResolution(int res) {this.res = res;}

    @Override
    public int resolution(){return res;}

    @Override
    public VertexFormat.Mode format() {return VertexFormat.Mode.TRIANGLE_FAN;}

    @Override
    public void copy(float[] out){
        out[0] = left();
        out[1] = top();
        out[2] = width();
        out[3] = height();
        out[4] = zLevel();
        out[5] = radiusX();
        out[6] = radiusY();
    }
    public void copyInner(float[] out){
        out[5] = radiusX();
        out[6] = radiusY();

        out[0] = left() + out[5];
        out[1] = top() + out[6];
        out[2] = width() - out[5];
        out[3] = height() - out[6];
        out[4] = zLevel();
    }

    @Override
    public void normalize(float divisor, float[] out) {
        float invDiv = 1.0f / divisor;
        out[0] = left() * invDiv;
        out[1] = top() * invDiv;
        out[2] = width() * invDiv;
        out[3] = height() * invDiv;
        out[4] = zLevel();
        out[5] = radiusX() * invDiv * (out[2] / width());
        out[6] = radiusY() * invDiv * (out[3] / height());
    }
}
