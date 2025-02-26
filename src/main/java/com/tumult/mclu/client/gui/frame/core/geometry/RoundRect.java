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
    public int resolution(){return res;}
    @Override
    public VertexFormat.Mode mode() {return VertexFormat.Mode.TRIANGLE_FAN;}

    @Override
    public void copyTo(float[] out){
        out[0] = x;
        out[1] = y;
        out[2] = w;
        out[3] = h;
        out[4] = z;
        out[5] = r;
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
        out[0] = x * invDiv;
        out[1] = y * invDiv;
        out[2] = w * invDiv;
        out[3] = h * invDiv;
        out[4] = z;
        out[5] = r * invDiv * (out[2] / w);
    }
}
