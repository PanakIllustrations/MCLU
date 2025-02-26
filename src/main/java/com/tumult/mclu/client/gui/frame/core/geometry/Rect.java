package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public class Rect implements IRect, IDirtyable, IParentable, IDrawable {
    protected float x, y, w, h, z;
    protected IContainer parent = null;
    protected boolean dirty = true;

    public Rect() {this(0,0,0,0,0);}
    public Rect(Rect r){this(r.x, r.y, r.w, r.h, r.z);}
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
            markDirty();
            if (parent != null) {
                parent.markLayoutDirty();
            }
        }
    }

    // IDimension implementation
    @Override public float width() { return w; }
    @Override public float height() { return h; }
    @Override public void setWH(float w, float h) {
        if (this.w != w || this.h != h) {
            this.w = w;
            this.h = h;
            markDirty();
            if (parent != null) {
                parent.markLayoutDirty();
            }
        }
    }

    // IDrawable implementation
    @Override public int resolution() { return 1; }
    @Override public VertexFormat.Mode mode() { return VertexFormat.Mode.QUADS; }
    @Override public float depth() { return z; }
    @Override public void setDepth(float zLevel) {
        if (this.z != zLevel) {
            this.z = zLevel;
            markDirty();
        }
    }
    @Override public void copyTo(float[] out) {
        out[0] = x;
        out[1] = y;
        out[2] = w;
        out[3] = h;
        out[4] = z;
    }

    // IDirtyable implementation
    @Override public boolean isDirty() { return dirty; }
    @Override public void markDirty() { dirty = true; }
    @Override public void clearDirty() { dirty = false; }

    // IParentable implementation
    @Override public IContainer getParent() { return parent; }
    @Override public void setParent(IContainer parent) { this.parent = parent; }
}
