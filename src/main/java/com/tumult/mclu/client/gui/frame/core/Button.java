package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.List;

public class Button extends DrawableRect {
    protected String label;

    public Button(Vector4DRect bounds, ResourceLocation resource, Vector4DRect texture, String label) {
        super(bounds, resource, texture);
        this.label = label;
    }
    public Button(Vector4DRect bounds, Color color, float radius, String Label) {
        super(bounds, color, radius);
        this.label = Label;
    }

    @Override
    public void update(Vector2DPoint position, List<Integer> pressedButtons) {
        super.update(position, pressedButtons);
        // Handle button-specific interactions
    }

    @Override
    public void draw(GuiGraphics guiGraphics) {
        super.draw(guiGraphics);
        // Implement additional drawing for the label
    }
}
