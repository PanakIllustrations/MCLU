package com.tumult.mclu.client.gui.frame.core.review;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;


public class GuiHUD {

private static boolean setup = false;

    public static final IGuiOverlay GUI_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        final Node backpack = Registry.icon.get().backpack;
        final Node mouse_cursor = Registry.icon.get().mouse_cursor;
        final Node passport = Registry.icon.get().passport;
        final Node map = Registry.icon.get().map;
        final Node hud = Registry.icon.get().hud;

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
                backpack.dragTo(cursorPos, buttons[0]);
                passport.dragTo(cursorPos, buttons[0]);
                map.dragTo(cursorPos, buttons[0]);
            }
            hud.render();
        }
    };
}