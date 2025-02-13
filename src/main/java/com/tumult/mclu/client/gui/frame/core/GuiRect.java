package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class GuiRect<T extends IRect> implements IRenderable {
    public final IRect rect;

    GuiRect(T r) {
        rect = r;
    }

    public void render(GuiGraphics guiGraphics){
        render(guiGraphics, Color.WHITE);
    }

    public void render(GuiGraphics guiGraphics, Color color) {
    }
}
