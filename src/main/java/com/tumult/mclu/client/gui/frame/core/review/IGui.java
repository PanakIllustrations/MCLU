package com.tumult.mclu.client.gui.frame.core.review;

import com.tumult.mclu.client.gui.frame.core.geometry.IRect;

public class IGui<T extends IRect>{
    T rect;
    IGui(T rect) {
        this.rect = rect;
    }

    float initialX;
    float initialY;
    boolean isDragging;

    void dragTo(float[] pos, boolean button){
        if (button && rect.contains(pos[0], pos[1]) && !isDragging) {
            isDragging = true;
            initialX = pos[0] - rect.left();
            initialY = pos[1] - rect.top();
        }
        if (isDragging) {
            rect.moveBy(
                    pos[0] - initialX - rect.left(),
                    pos[1] - initialY - rect.top()
            );
        }
        if (!button && isDragging) {
            isDragging = false;
        }
    }
    T getRect(){
        return rect;
    }
}
