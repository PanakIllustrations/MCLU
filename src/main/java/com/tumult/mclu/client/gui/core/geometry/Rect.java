package com.tumult.mclu.client.gui.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

import java.util.List;

public class Rect implements IRect {
    protected float x, y, w, h, z;
    protected boolean isDirty = true;
    protected IRect parent = null;
    protected List<IRect> children = null;

    public Rect() {this(0,0,0,0,0);}
    public Rect(float w, float h) {this(0,0,w,h,0);}
    public Rect(float x, float y, float w, float h, float z) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.z = z;
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

    // IPosition implementation
    @Override public float left() { return x; }
    @Override public float top() { return y; }
    @Override public void setUL(float x, float y) {
        if (this.x != x || this.y != y) {
            this.x = x;
            this.y = y;
        }
    }

    // IDimension implementation
    @Override public float width() { return w; }
    @Override public float height() { return h; }
    @Override public void setWH(float w, float h) {
        if (this.w != w || this.h != h) {
            this.w = w;
            this.h = h;
        }
    }

    // IDrawable implementation
    @Override public int resolution() { return 1; }
    @Override public VertexFormat.Mode mode() { return VertexFormat.Mode.QUADS; }
    @Override public float depth() { return z; }
    @Override public void setDepth(float zLevel) {
        this.z = zLevel;
    }
    @Override public void copyTo(float[] out) {
        out[0] = x;
        out[1] = y;
        out[2] = w;
        out[3] = h;
        out[4] = z;
    }

    // IParentable implementation
    @Override public IRect getParent() { return parent; }
    @Override public void setParent(IRect parent) { this.parent = parent; }
}
