package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;

public abstract class AbstractGuiElement<T extends IRect> {
    protected VertexFormat format;
    protected final float[] points;
    protected float[] color;

    private float dragOffsetX, dragOffsetY;
    private boolean isDragging = false;
    T rect;

    AbstractGuiElement(T rect) {
        this.rect = rect;
        points = new float[rect.res() * 8];
        BufferProvider.updatePoints(rect, points);
    }

    public void handleDragging(float[] pos, boolean[] buttons) {
        if (buttons[0] & rect.contains(pos) & !isDragging) {
            isDragging = true;
            dragOffsetX = pos[0] - rect.left();
            dragOffsetY = pos[1] - rect.top();
        }
        if (isDragging) {
            rect.setUL(pos[0] - dragOffsetX, pos[1] - dragOffsetY);
        }
        if (isDragging & !buttons[0]) {
            isDragging = false;
        }
    }

    abstract void updateShaderInstance();
    abstract void render();
}
