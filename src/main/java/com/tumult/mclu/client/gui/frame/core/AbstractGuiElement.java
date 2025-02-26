package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import com.tumult.mclu.client.gui.frame.core.util.PointArrayPool;
import com.tumult.mclu.client.gui.frame.core.util.UIProfiler;

public abstract class AbstractGuiElement<T extends IRect> {
    protected VertexFormat format;
    protected float[] points;
    protected float[] color;

    private float dragOffsetX, dragOffsetY;
    protected boolean isDragging = false;
    public T rect;

    private static boolean enableProfiling = false;
    private String profileName;

    protected final String id;
    private static int nextId = 0;

    protected AbstractGuiElement(T rect) {
        this.rect = rect;
        this.id = getClass().getSimpleName() + "_" + (nextId++);
        this.profileName = "render_" + id;

        points = PointArrayPool.obtain(rect.res() * 8);
        BufferProvider.updatePoints(rect, points);
    }

    public void dispose() {
        PointArrayPool.recycle(points);
    }

    public void handleDragging(float[] pos, boolean[] buttons) {
        if (buttons[0] && rect.contains(pos[0] - rect.getAbsoluteLeft() + rect.left(),
                pos[1] - rect.getAbsoluteTop() + rect.top()) && !isDragging) {
            isDragging = true;
            dragOffsetX = pos[0] - rect.getAbsoluteLeft();
            dragOffsetY = pos[1] - rect.getAbsoluteTop();
        }

        if (isDragging) {
            rect.setUL(pos[0] - dragOffsetX, pos[1] - dragOffsetY);
            // Mark the parent's layout as dirty if this element has a parent
            if (rect.getParent() != null) {
                rect.getParent().markLayoutDirty();
            }
        }

        if (isDragging && !buttons[0]) {
            isDragging = false;
        }
    }
    public void render() {
        if (enableProfiling) {
            UIProfiler.start(profileName);
        }

        // Check if element is visible before rendering
        if (isVisible()) {
            doRender();
        }

        if (enableProfiling) {
            UIProfiler.end(profileName);
        }
    }

    public boolean isVisible() {
        return true;
    }

    protected abstract void doRender();
    abstract void updateShaderInstance();
    public abstract void addToBuffer(BufferBuilder builder);
}
