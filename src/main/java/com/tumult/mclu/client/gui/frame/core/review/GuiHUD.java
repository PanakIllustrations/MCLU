package com.tumult.mclu.client.gui.frame.core.review;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;


public class GuiHUD {

private static boolean setup = false;

    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        final GuiSprite backpack = Registry.icon.get().backpack;
        final GuiSprite mouse_cursor = Registry.icon.get().mouse_cursor;
        final GuiSprite passport = Registry.icon.get().passport;
        final GuiSprite map = Registry.icon.get().map;
        final GuiShape rect = Registry.icon.get().rect;

        UIManager.init(screenWidth, screenHeight);
        if (!setup) {
            backpack.rect.setUL((float) screenWidth / 2 + 92,(float) screenHeight - 40);
            passport.rect.setUL((float) screenWidth / 2 + 92 + 20,(float) screenHeight - 40);
            setup = true;
        }

        if (player != null) {
            if (UIManager.isCursorVisible()) {
                float[] cursorPos = UIManager.getMousePos();
                boolean[] buttons = UIManager.getMouseButtons();
                mouse_cursor.rect.setUL(cursorPos);
                mouse_cursor.render();

                passport.handleDragging(cursorPos, buttons);
                backpack.handleDragging(cursorPos, buttons);
                map.handleDragging(cursorPos, buttons);
                rect.handleDragging(cursorPos, buttons);
            }
            backpack.render();
            passport.render();
            map.render();
            rect.render();
        }
    };
}