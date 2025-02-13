package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class GuiShape<T extends IRect> {
    public final IRect rect;
    private final VertexFormat format = DefaultVertexFormat.POSITION_COLOR;

    GuiShape(T r) {
        rect = r;
    }
    public void draw(GuiGraphics g) {

    }
}
