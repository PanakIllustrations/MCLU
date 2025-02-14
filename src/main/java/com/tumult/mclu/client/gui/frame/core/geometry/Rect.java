package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public class Rect implements IRect {
    private float x, y, w, h, z;
    private boolean isDragging = false;
    private float initialClickX = 0;
    private float initialClickY = 0;

    public Rect(float w, float h) {this(0,0,w,h,1);}
    public Rect(float x, float y, float w, float h, float z) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.z = z;
    }
    public Rect(Rect r){
        this(
            r.left(),
            r.top(),
            r.width(),
            r.height(),
            r.zLevel()
        );
    }

    public Rect(float[] in){
        this(
            in[0],
            in[1],
            in[2],
            in[3],
            in[4]
        );
    }
    @Override
    public void copy(float[] out) {
        out[0] = left();
        out[1] = top();
        out[2] = width();
        out[3] = height();
        out[4] = zLevel();
    }
    // basic rect parameters
    @Override public float left() {return x;}
    @Override public float top() {return y;}

    @Override public float width() {return w;}
    @Override public float height() {return h;}
    @Override public float zLevel() {return z;}
    @Override public int resolution() {return 1;}
    @Override public VertexFormat.Mode format() {return VertexFormat.Mode.QUADS;}
    // position and dimensions
    @Override public void getUL(float[] out) {out[0] = x; out[1] = y;}
    @Override public void getWH(float[] out) {out[0] = w; out[1] = h;}
    @Override public void setUL(float[] in) {x = in[0]; y = in[1];}
    @Override public void setWH(float[] in) {w = in[0]; h = in[1];}
    @Override public void setZ(float z) {this.z = z;}

    @Override
    public void dragTo(float x, float y, boolean button){
        if (button && contains(x, y) && !isDragging) {
            isDragging = true;
            initialClickX = x - left();
            initialClickY = y - top();
        }
        if (isDragging) {
            moveBy(
                x - initialClickX - left(),
                y - initialClickY - top()
            );
        }
        if (!button && isDragging) {
            isDragging = false;
        }
    }

    @Override
    public void setUL(float x, float y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public void setWH(float w, float h) {
        this.w = w;
        this.h = h;
    }
    @Override
    public void setBR(float b, float r) {
        x = b - w;
        y = r - h;
    }

}
