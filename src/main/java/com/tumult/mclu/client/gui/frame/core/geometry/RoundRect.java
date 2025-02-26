package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.tumult.mclu.client.gui.frame.core.BufferProvider;

public class RoundRect extends Rect implements IRect {
    protected float r;
    protected int res = 10;

    public RoundRect(float x, float y, float w, float h, float z, float r) {
        super(x, y, w, h, z);
        this.r = r;
    }

    public float radius(){return r;}
    public void setR(float r) {this.r = r;}
    public void setRes(int res) {this.res = res;}

    @Override
    public int res(){return res;}
    @Override
    public VertexFormat.Mode mode() {return VertexFormat.Mode.TRIANGLE_FAN;}

    @Override
    public void copyTo(float[] out){
        out[X] = x;
        out[Y] = y;
        out[W] = w;
        out[H] = h;
        out[Z] = z;
        out[R] = r;
    }
    public void copyInnerTo(float[] out){
        out[0] = r; out[1] = r; // top left
        out[2] = r; out[3] = height() - r; // bottom left
        out[4] = width() - r; out[5] = height() - r; // bottom right
        out[6] = width() - r; out[7] = r; // top right
    }

    @Override
    public void normalize(float divisor, float[] out) {
        float invDiv = 1.0f / divisor;
        out[X] = x * invDiv;
        out[Y] = y * invDiv;
        out[W] = w * invDiv;
        out[H] = h * invDiv;
        out[Z] = z;
        out[R] = r * invDiv * (out[2] / w);
    }
}
