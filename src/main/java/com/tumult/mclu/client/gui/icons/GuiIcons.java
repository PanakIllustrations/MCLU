package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.core.DrawableRect;
import com.tumult.mclu.client.gui.frame.core.UIElement;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.resources.ResourceLocation;


public class GuiIcons {
    public final UIElement backpack;
    public final UIElement map;
    public final UIElement passport;
    public final DrawableRect mouse_cursor;

    public GuiIcons() {
        this.backpack = registerUIComponent("backpack", 16, 16, 16, 16);
        this.map = registerUIComponent("map", 16, 16, 16, 16);
        this.passport = registerUIComponent("passport", 16, 16, 16, 16);
        this.mouse_cursor = registerDrawableIcon("mouse_cursor", 9, 17, 32, 32);

    }
    public UIElement registerUIComponent(String name, double width, double height, double textureWidth, double textureHeight) {
        return new UIElement(
                getResource(name),
                new Vector4DRect(0, 0, width, height),
                new Vector4DRect(0, 0, textureWidth, textureHeight).normalize(new Vector2DPoint(textureWidth, textureHeight))
        );
    }
    public DrawableRect registerDrawableIcon(String name, double width, double height, double textureWidth, double textureHeight) {
        return new DrawableRect(
                getResource(name),
                new Vector4DRect(0, 0, width, height),
                new Vector4DRect(0, 0, textureWidth, textureHeight).normalize(new Vector2DPoint(textureWidth, textureHeight))
        );
    }

    private ResourceLocation getResource(String name) {
        return new ResourceLocation(McluConstants.MOD_ID, "/textures/gui/" + name + ".png");
    }
}


/*
    private final float iconPosX;
    private final float iconPosY;
    public final float iconWidth;
    public final float iconHeight;
    private final float textureWidth;
    private final float textureHeight;

    public GuiIcon(ResourceLocation location, int iconWidth, int iconHeight, int iconPosX, int iconPosY, int textureWidth, int textureHeight) {
        this.location = location;
        this.iconPosX = iconPosX;
        this.iconPosY = iconPosY;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }



    */

