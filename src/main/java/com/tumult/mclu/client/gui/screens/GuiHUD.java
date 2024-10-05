package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.client.gui.frame.core.DrawableRect;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.icons.GuiCursor;
import com.tumult.mclu.client.gui.icons.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GuiHUD {
    private static final ResourceLocation resourceLocation = new ResourceLocation("mclu", "textures/gui/rounded.png");
    private static final Vector2DPoint iconDimensions = new Vector2DPoint(9, 9);
    private static final Vector2DPoint textureDimensions = new Vector2DPoint(16, 16);
    private static boolean initialized = false;
    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            UIComponent backpack = IconUtils.getIcon().backpack;
            DrawableRect cursor = IconUtils.getIcon().mouse_cursor;


            if (!initialized) {
                backpack.setUl(new Vector2DPoint((double) screenWidth / 2 + 94, (double) screenHeight - (2 + 16)));
                initialized = true;
            }

            double mouseX = cursor.getUl().x;
            double mouseY = cursor.getUl().y;
            boolean mousePressed = Minecraft.getInstance().mouseHandler.isLeftPressed();
            backpack.update(mouseX, mouseY, mousePressed ? 1 : 0, screenWidth, screenHeight);
            backpack.draw(guiGraphics);
            rect.draw(guiGraphics);
            rect.move(mouseX, mouseY, screenWidth, screenHeight, mousePressed);
            //rect.scale(mouseX, mouseY, screenWidth, screenHeight, Minecraft.getInstance().mouseHandler.isRightPressed());
            //passport.draw(guiGraphics, position.add(18, 0));
            //map.draw(guiGraphics, position.add(16, 0));

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