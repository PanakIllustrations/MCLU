package com.tumult.mclu.client.gui.frame;

import com.tumult.mclu.client.gui.geometry.Vector2DPoint;
import net.minecraft.client.gui.GuiGraphics;

public interface IDrawable {
    public void draw(GuiGraphics guiGraphics, Vector2DPoint position);
    public void preDraw(GuiGraphics guiGraphics);
    public void postDraw(GuiGraphics guiGraphics);
}
