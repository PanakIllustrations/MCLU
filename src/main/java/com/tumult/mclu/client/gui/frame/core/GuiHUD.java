package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import com.tumult.mclu.client.gui.frame.old.UIManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Vector2f;

public class GuiHUD {

    private static final GuiSprite<IRect> mouse_cursor = IconUtils.getIcon().mouse_cursor;
    private static final GuiSprite<IRect> backpack = IconUtils.getIcon().backpack;
    private static final GuiSprite<IRect> map = IconUtils.getIcon().map;
    private static final GuiSprite<IRect> passport = IconUtils.getIcon().passport;
    private static final boolean setup = false;

    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        UIManager.init(screenWidth, screenHeight);
        if (!setup) {
            backpack.rect.setUL((float) screenWidth / 2 + 92,(float) screenHeight - 40);
        }

        if (player != null) {

            backpack.render(guiGraphics);

            if (UIManager.isCursorVisible()) {
                float[] cursorPos = UIManager.getMousePos();
                boolean[] buttons = UIManager.getMouseButtons();
                mouse_cursor.rect.setUL(cursorPos);
                mouse_cursor.render(guiGraphics);
                backpack.rect.dragTo(cursorPos, buttons[0]);
            }
            //roundRect.render(guiGraphics);
            //rect.render(guiGraphics);

            map.render(guiGraphics);
            passport.render(guiGraphics);
        }
    };
}
/*
blit(ResourceLocation location, int screenPosX, int screenPosY, int zLevel, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconPosX, int iconPosY, int iconWidth, int iconHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconWidth1, int iconHeight1, float iconPosX, float iconPosY, int iconWidth2, int iconHeight2, int textureWidth, int textureHeight);
blit(ResourceLocation location, int left, int right, int top, int bottom, int zLevel, int iconWidth2, int iconHeight2, float iconPosX, float iconPosY, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
*/