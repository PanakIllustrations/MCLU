package com.tumult.mclu.client.gui.icons;

import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.core.CloseButton;
import com.tumult.mclu.client.gui.frame.core.ToggleButton;
import com.tumult.mclu.client.gui.frame.geometry.DrawableSprite;
import com.tumult.mclu.client.gui.frame.core.ResizeHandle;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.resources.ResourceLocation;

public class GuiIcons {
    public final DrawableSprite map;
    public final DrawableSprite passport;
    public final DrawableSprite mouse_cursor;
    public final CloseButton bankCross;
    public final ResizeHandle bankResize;

    // Toggle button sprites
    public final DrawableSprite backpackOpen;
    public final DrawableSprite backpackClosed;
    public final DrawableSprite backpack_reflection;
    public final ToggleButton backpack;


    public GuiIcons() {
        // Reflection sprite scaled to 16x16 (was 128x128)
        this.backpack_reflection = registerDrawableSprite("backpack_reflection", 16, 16, 128, 128, 1f);
        this.map = registerDrawableSprite("map", 16, 16);
        this.passport = registerDrawableSprite("passport", 16, 16);
        this.mouse_cursor = registerDrawableSprite("mouse_cursor", 9, 17, 32, 32, 100f);

        this.bankCross = new CloseButton(
                getResource("bank_cross"),
                new Vector4DRect(0, 0, 16, 16),
                new Vector4DRect(0, 0, 32, 32).normalize(new Vector2DPoint(32, 32)),
                1f
        );

        this.bankResize = new ResizeHandle(
                getResource("bank_resize"),
                new Vector4DRect(0, 0, 16, 16),
                new Vector4DRect(0, 0, 32, 32).normalize(new Vector2DPoint(32, 32)),
                1f
        );

        // Create open/closed sprites for toggle button
        this.backpackOpen = registerDrawableSprite("backpack", 16, 16, 16, 16, 1f);
        this.backpackClosed = registerDrawableSprite("backpack", 16, 16, 16, 16, 1f);

        this.backpack = new ToggleButton(backpackOpen, backpackClosed);

    }

    private ResourceLocation getResource(String name) {
        return new ResourceLocation(McluConstants.MOD_ID, "/textures/gui/" + name + ".png");
    }

    public DrawableSprite registerDrawableSprite(String name, double width, double height) {
        return new DrawableSprite(
                getResource(name),
                new Vector4DRect(0, 0, width, height),
                new Vector4DRect(0, 0, width, height).normalize(new Vector2DPoint(width, height)),
                1f
        );
    }

    public DrawableSprite registerDrawableSprite(String name, double iconWidth, double iconHeight,
                                                 double textureWidth, double textureHeight, float zLevel) {
        return new DrawableSprite(
                getResource(name),
                new Vector4DRect(0, 0, iconWidth, iconHeight),
                new Vector4DRect(0, 0, iconWidth, iconHeight).normalize(new Vector2DPoint(textureWidth, textureHeight)),
                zLevel
        );
    }
}