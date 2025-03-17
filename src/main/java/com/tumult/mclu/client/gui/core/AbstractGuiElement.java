package com.tumult.mclu.client.gui.core;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.tumult.mclu.client.gui.core.geometry.Rect;
import com.tumult.mclu.client.gui.core.util.BufferProvider;
import com.tumult.mclu.client.gui.core.util.PointArrayPool;

import java.io.Serializable;

public abstract class AbstractGuiElement<T extends Rect> implements Serializable {
    public final T rect;

    protected float[] points;
    protected float[] color;
    protected boolean visible = true;
    protected VertexFormat format;

    protected AbstractGuiElement(T rect) {
        this.rect = rect;
        points = PointArrayPool.obtain(rect.resolution() * 8);
        BufferProvider.updatePoints(rect, points);
    }

    public void render() {
        if (!visible) return;
        //if (rect.isDirty())
        update();
    }

    public void update(){
        BufferProvider.updatePoints(rect, points);
        //rect.clearDirty();
    }
}
