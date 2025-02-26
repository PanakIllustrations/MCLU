package com.tumult.mclu.client.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CustomAttributeHud {
    public enum AnimationState {
        IDLE,
        FLASH_DELAY,
        ANTICIPATION,
        INTERPOLATION,
        FOLLOW_THROUGH
    }

    public static class HudState {
        int screenX;
        int screenY;
    }

    private int tickDelay = 5;
    //private static final DrawableRect rect = new DrawableRect(new Vector4DRect(160 / 2f - 92, 46, 4 * 8, 7), new Color(0xbb1313), 3.5f);

    public static final IGuiOverlay CUSTOM_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && !player.isCreative()) {
            //rect.draw(guiGraphics);
        }
    };
}

