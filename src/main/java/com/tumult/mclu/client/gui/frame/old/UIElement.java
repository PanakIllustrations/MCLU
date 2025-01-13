package com.tumult.mclu.client.gui.frame.old;

import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public abstract class UIElement {
    protected Vector4DRect bounds;
    protected Color renderColor;
    protected boolean visible = true;

    public abstract void render(GuiGraphics g);
    public abstract void update();

    public void setPosition(float x, float y) {
        this.bounds.setUl(x, y);
    }

    public void setSize(float width, float height) {
        this.bounds.setWh(width, height);
    }

    public  void setVisible(boolean visible) {
        this.visible = visible;
    }
}
