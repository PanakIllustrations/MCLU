package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public class Rect implements IRect {
    protected float x, y, w, h, z;
    protected IContainer parent = null;
    protected boolean dirty = true;

    public Rect() {this(0,0,0,0,0);}
    public Rect(Rect r){this(r.x, r.y, r.w, r.h, r.z);}
    public Rect(float w, float h, float z) {this(0,0,w,h,z);}
    public Rect(float x, float y, float w, float h, float z) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.z = z;
    }

    public Rect(float[] in){
        this(
            in[X],
            in[Y],
            in[W],
            in[H],
            in[Z]
        );
    }
    @Override
    public void copyTo(float[] out) {
        out[X] = x;
        out[Y] = y;
        out[W] = w;
        out[H] = h;
        out[Z] = z;
    }

    // basic rect parameters
    @Override public float left() {return x;}
    @Override public float top() {return y;}
    @Override public float width() {return w;}
    @Override public float height() {return h;}
    @Override public float zLevel() {return z;}

    // parent-child relationship
    @Override public IContainer getParent() {return parent;}
    @Override public void setParent(IContainer parent) {this.parent = parent;}

    @Override public int res() {return 1;}
    @Override public VertexFormat.Mode mode() {return VertexFormat.Mode.QUADS;}

    // Dirty flag support
    @Override public boolean isDirty() {return dirty;}
    @Override public void markDirty() {dirty = true;}
    @Override public void clearDirty() {dirty = false;}

    // position and dimensions
    @Override
    public void setUL(float x, float y) {
        if (this.x != x || this.y != y) {
            this.x = x;
            this.y = y;
            markDirty();
            if (parent != null) {
                parent.markLayoutDirty();
            }
        }
    }
    @Override
    public void setWH(float w, float h) {
        if (this.w != w || this.h != h) {
            this.w = w;
            this.h = h;
            markDirty();
            if (parent != null) {
                parent.markLayoutDirty();
            }
        }
    }

    @Override
    public void setBR(float b, float r) {
        setUL(b - w, r - h);
    }

    @Override
    public void setZ(float z) {
        if (this.z != z) {
            this.z = z;
            markDirty();
        }
    }

    public Rect copy(){
        return new Rect(this);
    }
}
