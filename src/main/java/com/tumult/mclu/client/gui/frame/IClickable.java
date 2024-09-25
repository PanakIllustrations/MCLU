package com.tumult.mclu.client.gui.frame;

public interface IClickable extends IDrawable {
    boolean containsPoint(double x, double y);
    void mouseDownEvent(double x, double y, int button);
    void mouseUpEvent(double x, double y, int button);
}
