package com.tumult.mclu.client.gui.frame;

import com.tumult.mclu.client.gui.geometry.IRectangle;

public class MouseEventFrame<T extends IRectangle> implements IMouseEventFrame {
    IMouseDown onMouseDown;
    IMouseUp onMouseUp;

    boolean isActive = true;
    boolean isVisible = true;

    T rect = null;

    public MouseEventFrame(T rect) {
        This.rect = rect;
    }

}
