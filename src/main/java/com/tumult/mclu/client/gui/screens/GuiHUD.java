package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.client.gui.frame.DrawableRect;
import com.tumult.mclu.client.gui.icons.GuiCursor;
import com.tumult.mclu.client.gui.icons.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Vector2d;

public class GuiHUD {
    private static Vector2d position;
    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            position = new Vector2d((double) screenWidth / 2 + 94, (double) screenHeight - (2 + 16));
            DrawableRect backpack = IconUtils.getIcon().backpack;
            DrawableRect map = IconUtils.getIcon().map;
            DrawableRect passport = IconUtils.getIcon().passport;
            DrawableRect cursor = IconUtils.getIcon().mouse_cursor;

            //backpack.setRightOfThis(map);
            //map.setRightOfThis(passport);

            backpack.draw(guiGraphics, position);
            passport.draw(guiGraphics, position.add(18, 0));
            map.draw(guiGraphics, position.add(16, 0));

            if (GuiCursor.isCursorVisible()) {
                cursor.draw(guiGraphics, GuiCursor.getMousePos()); // Draw the cursor at the clamped mouse position
            }
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