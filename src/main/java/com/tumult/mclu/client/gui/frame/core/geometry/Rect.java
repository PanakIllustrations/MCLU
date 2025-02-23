package com.tumult.mclu.client.gui.frame.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

public class Rect implements IRect {
    protected float x, y, w, h, z;
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
    @Override public int res() {return 1;}
    @Override public VertexFormat.Mode mode() {return VertexFormat.Mode.QUADS;}
    // position and dimensions
    @Override public void setUL(float[] in) {setUL(in[X],in[Y]);}
    @Override public void setZ(float z) {this.z = z;}

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
    public Rect copy(){
        return new Rect(this);
    }

}
