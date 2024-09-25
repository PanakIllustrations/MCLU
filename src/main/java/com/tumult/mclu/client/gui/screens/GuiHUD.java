package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.client.gui.icons.Clickable;
import com.tumult.mclu.client.gui.icons.IconUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GuiHUD {
    private static final Clickable.ClickableIcon icon = IconUtils.INSTANCE.getClickable().backpack;
    public static final IGuiOverlay BACKPACK_ICON = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null && icon != null) {
            //icon.draw(guiGraphics, (double) screenWidth / 2, (double) screenHeight / 2);
        } else if (icon == null) {
            System.out.println("Icon not found");
        }
    };
}
