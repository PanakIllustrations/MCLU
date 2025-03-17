package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.client.gui.core.GuiShape;
import com.tumult.mclu.client.gui.core.geometry.RoundRect;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;

public class GuiHud {
    private static final GuiShape rect = new GuiShape(new RoundRect(10, 10, 50, 50, 0, 5), Color.CYAN);
    public static final IGuiOverlay GUI_HUD = (gui, guGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            rect.render();
        }
    };
}
