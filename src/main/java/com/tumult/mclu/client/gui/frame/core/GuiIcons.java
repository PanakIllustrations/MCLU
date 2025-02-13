package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import com.tumult.mclu.client.gui.frame.core.geometry.Rect;
import net.minecraft.client.Minecraft;


public class GuiIcons {
    public final GuiSprite<IRect> backpack;
    public final GuiSprite<IRect> map;
    public final GuiSprite<IRect> passport;
    public final GuiSprite<IRect> mouse_cursor;

    GuiIcons(){
        backpack = registerGuiIcon("backpack", new Rect( 16, 16), 16);
        map = registerGuiIcon("map", new Rect(16, 16), 16);
        passport = registerGuiIcon("passport", new Rect(16,16), 16);
        mouse_cursor = registerGuiIcon("mouse_cursor", new Rect(9,17), 32);
    }

    public GuiSprite<IRect> registerGuiIcon(String name, IRect rect, int size) {
        return new GuiSprite<>(name, rect, size);
    }
}