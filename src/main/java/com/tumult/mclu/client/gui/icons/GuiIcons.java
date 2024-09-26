package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.McluConstants;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2d;


public class GuiIcons {
    public final DrawableIcon backpack;
    public final DrawableIcon map;
    public final DrawableIcon passport;
    public final DrawableIcon mouse_cursor;

    public GuiIcons() {
        this.backpack = registerDrawableIcon("backpack", 16, 16, 16, 16);
        this.map = registerDrawableIcon("map", 16, 16, 16, 16);
        this.passport = registerDrawableIcon("passport", 16, 16, 16, 16);
        this.mouse_cursor = registerDrawableIcon("mouse_cursor", 9, 17, 32, 32);

    }
    public DrawableIcon registerDrawableIcon(String name, int width, int height, int textureWidth, int textureHeight) {
        return new DrawableIcon(
                new ResourceLocation(McluConstants.MOD_ID, "/textures/gui/" + name + ".png"),
                new Vector2d(width, height),
                new Vector2d(textureWidth, textureHeight)
        );
    }
    //public ClickableIcon registerCLickableIcon(String name, int width, int height, int textureWidth, int textureHeight) {
        //return new ClickableIcon(registerDrawableIcon(name, width, height, textureWidth, textureHeight));
    //}
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

