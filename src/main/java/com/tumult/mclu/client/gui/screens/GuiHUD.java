package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.client.gui.frame.core.DrawableRect;
import com.tumult.mclu.client.gui.frame.core.UIElement;
import com.tumult.mclu.client.gui.frame.core.UIManager;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import com.tumult.mclu.client.gui.icons.GuiCursor;
import com.tumult.mclu.client.gui.icons.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;
import java.util.ArrayList;

public class GuiHUD {

    private static final Color color = new Color(1, 0, 0, 0);
    private static final GuiCursor cursor = new GuiCursor();
    //private static final UIElement rect = new UIElement(new Vector4DRect(0, 0, 50, 30), color, 7);
    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        Vector2DPoint position = UIManager.getMousePosition();
        java.util.List<Integer> pressedButtons = UIManager.getMouseButtons();

        UIElement backpack = IconUtils.getIcon().backpack;
        DrawableRect cursor = IconUtils.getIcon().mouse_cursor;

        if (player != null) {
            cursor.bounds.setUl(UIManager.getMousePosition());
            cursor.draw(guiGraphics);
            backpack.update(position, pressedButtons);
            backpack.draw(guiGraphics);
            //rect.update(position, pressedButtons);
            //rect.draw(guiGraphics);
        }



        // Only draw the cursor if the player exists and the cursor is set to be visible

    };

}
/*
blit(ResourceLocation location, int screenPosX, int screenPosY, int zLevel, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconPosX, int iconPosY, int iconWidth, int iconHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconWidth1, int iconHeight1, float iconPosX, float iconPosY, int iconWidth2, int iconHeight2, int textureWidth, int textureHeight);
blit(ResourceLocation location, int left, int right, int top, int bottom, int zLevel, int iconWidth2, int iconHeight2, float iconPosX, float iconPosY, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
*/