package com.tumult.mclu.client.gui.frame.core.review;

import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import com.tumult.mclu.client.gui.frame.core.geometry.Rect;
import com.tumult.mclu.client.gui.frame.core.geometry.RoundRect;
import net.minecraft.client.Minecraft;

import java.awt.*;


public class Registry {
    public final GuiSprite backpack;
    public final GuiSprite map;
    public final GuiSprite passport;
    public final GuiSprite mouse_cursor;
    public final GuiShape rect;

    Registry(){
        mouse_cursor = registerGuiSprite(new Rect(9,17, 1), "mouse_cursor", 32);
        backpack = registerGuiSprite(new Rect(16, 16, 2), "backpack", 16);
        map = registerGuiSprite( new Rect(16, 16, 2), "map", 16);
        passport = registerGuiSprite( new Rect(16,16, 2), "passport", Color.RED, 16);
        rect = registerGuiShape( new RoundRect(20, 40 ,30, 70, 2,5), Color.RED);
    }

    public GuiSprite registerGuiSprite(IRect rect, String name, Color color, int size) {
        return new GuiSprite(rect, name, size, color);
    }
    public GuiSprite registerGuiSprite(IRect rect, String name, int size) {
        return new GuiSprite(rect, name, size, Color.WHITE);
    }
    public GuiShape registerGuiShape(RoundRect rect, Color color) {
        return new GuiShape(rect, color);
    }

    public enum icon { // thread-safe singleton
        INSTANCE;
        private static Registry icon;
        public static Registry get() { // globalized access point
            if (icon == null) { // lazy initialization
                icon = new Registry();
            }
            return icon;
        }
    }
//
//    int defaultCanvasSize(float width, float height) {
//        int intValue = (int) Math.ceil(Math.max(width, height));
//        intValue--;
//        intValue |= intValue >> 1;
//        intValue |= intValue >> 2;
//        intValue |= intValue >> 4;
//        intValue |= intValue >> 8;
//        intValue |= intValue >> 16;
//        return intValue + 1;
//    }
}
