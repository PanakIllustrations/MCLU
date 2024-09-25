package com.tumult.mclu.client.gui.frame;

import net.minecraft.client.gui.GuiGraphics;
import org.joml.Vector2d;

public interface IDrawable{
    public void draw(GuiGraphics guiGraphics, Vector2d position);
    public void preDraw(GuiGraphics guiGraphics);
    public void postDraw(GuiGraphics guiGraphics);
}
