package com.tumult.mclu.client.gui.core.geometry;

import com.mojang.blaze3d.vertex.VertexFormat;

import java.util.List;

public interface IRect extends IPosition, IDimension, IDrawable, IParentable {
    default float right() {
        return left() + width();
    }
    default float bottom() {
        return top() + height();
    }
    default float centerX() {
        return left() + (width() * 0.5f);
    }
    default float centerY() {
        return top() + (height() * 0.5f);
    }
    default void setBR(float b, float r) {
        setUL(b - width(), r - height());
    }
    default void setBR(float[] v) {
        setBR(v[0], v[1]);
    }
    default void setCenter(float x, float y) {
        setUL(x - (width() * 0.5f), y - (height() * 0.5f));
    }
    default void setCenter(float[] v) {
        setCenter(v[0], v[1]);
    }
    default void getBR(float[] out) {
        out[0] = right();
        out[1] = bottom();
    }

    default void center(float[] out) {
        out[0] = centerX();
        out[1] = centerY();
    }

    default boolean contains(float x, float y) {
        return x >= left() && y >= top() && x <= right() && y <= bottom();
    }

    default boolean contains(float[] v) {
        return contains(v[0], v[1]);
    }
}

interface IPosition {
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

interface IDimension {
    float width();
    float height();

    void setWH(float w, float h);

    default void getWH(float[] out) {
        out[0] = width();
        out[1] = height();
    }
}

interface IDrawable {
    VertexFormat.Mode mode();
    float depth();
    int resolution();
    void setDepth(float depth);
    void copyTo(float[] out);

    default void normalize(float divisor, float[] out){
        for (int i = 0; i < out.length; i++) {
            out[i] /= divisor;
        }
    }
}

interface IParentable {
    IRect getParent();
    void setParent(IRect parent);

    default float getAbsoluteLeft(){
        if (getParent() != null && this != getParent()) {
            return getParent().getAbsoluteLeft() + ((IPosition)this).left();
        }
        return ((IPosition)this).left();
    }
    default float getAbsoluteTop(){
        if (getParent() != null && this != getParent()) {
            return getParent().getAbsoluteTop() + ((IPosition)this).top();
        }
        return ((IPosition)this).top();
    }
}

