package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.core.DrawableRect;
import com.tumult.mclu.client.gui.frame.core.DrawableSprite;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.resources.ResourceLocation;


public class GuiIcons {
    public final DrawableSprite backpack;
    public final DrawableSprite map;
    public final DrawableSprite passport;
    public final DrawableSprite mouse_cursor;
    public final DrawableSprite bankResize;
    public final DrawableSprite bankCross;

    public GuiIcons() {
        this.backpack = registerDrawableSprite("backpack", 16, 16);
        this.map = registerDrawableSprite("map", 16, 16);
        this.passport = registerDrawableSprite("passport", 16, 16);
        this.mouse_cursor = registerDrawableSprite("mouse_cursor", 9, 17, 32, 32, 0f);
        this.bankCross = registerDrawableSprite("bank_cross", 32, 32, 32, 32, 0f);
        this.bankResize = registerDrawableSprite("bank_resize", 32, 32, 32, 32, 0f);
    }
    private ResourceLocation getResource(String name) {
        return new ResourceLocation(McluConstants.MOD_ID, "/textures/gui/" + name + ".png");
    }
    public DrawableSprite registerDrawableSprite(String name, double width, double height) {
        // Register a sprite with UV coordinates normalized to [0, 1]
        return new DrawableSprite(
                getResource(name),
                new Vector4DRect(0, 0, width, height), // Absolute size rectangle
                new Vector4DRect(0, 0, width, height).normalize(new Vector2DPoint(width, height)), 1f // UV rectangle
        );
    }
    public DrawableSprite registerDrawableSprite(String name, double iconWidth, double iconHeight, double textureWidth, double textureHeight, float zLevel) {
        // Register a sprite with UV coordinates normalized based on the texture atlas dimensions
        return new DrawableSprite(
                getResource(name),
                new Vector4DRect(0, 0, iconWidth, iconHeight), // Absolute size rectangle
                new Vector4DRect(0, 0, iconWidth, iconHeight).normalize(new Vector2DPoint(textureWidth, textureHeight)), zLevel // UV rectangle
        );
    }
//    public DrawableSprite registerDrawableSprite(String name, double xOffset, double yOffset, double iconWidth, double iconHeight, double textureWidth, double textureHeight) {
//        // Register a sprite with UV coordinates normalized based on the texture atlas dimensions
//        // and an offset for the sprite's position on the texture atlas
//        return new DrawableSprite(
//                getResource(name),
//                new Vector4DRect(0, 0, iconWidth, iconHeight), // Absolute size rectangle
//                new Vector4DRect(xOffset, yOffset, xOffset + iconWidth, yOffset + iconHeight)
//                        .normalize(new Vector2DPoint(textureWidth, textureHeight)) // UV rectangle
//        );
//    }
//    public DrawableSprite registerDrawableSprite(String name, double xOffset, double yOffset, double iconWidth, double iconHeight, double textureWidth, double textureHeight, Vector4DRect mask) {
//        // Register a sprite with UV coordinates normalized based on the texture atlas dimensions
//        // an offset for the sprite's position on the texture atlas
//        // 4D mask to crop sprite's visible area
//        return new DrawableSprite(
//                getResource(name),
//                new Vector4DRect(0, 0, iconWidth, iconHeight), // Absolute size rectangle
//                new Vector4DRect(xOffset, yOffset, xOffset + iconWidth, yOffset + iconHeight)
//                        .normalize(new Vector2DPoint(textureWidth, textureHeight)) // UV rectangle
//        );
//    }
}