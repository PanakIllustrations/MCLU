package com.tumult.mclu.client.gui.frame;

import com.tumult.mclu.client.gui.geometry.Vector4DRect;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;

public abstract class GuiComponent extends AbstractWidget {

    public GuiComponent(int x, int y, int width, int height, Component component) {
        super(x, y, width, height, component);
    }
}
