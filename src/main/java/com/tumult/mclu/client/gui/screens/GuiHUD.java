package com.tumult.mclu.client.gui.screens;

import com.tumult.mclu.MCLU;
import com.tumult.mclu.client.gui.icons.ClickableIcon;
import com.tumult.mclu.client.gui.icons.GuiCursor;
import com.tumult.mclu.client.gui.icons.GuiIcon;
import com.tumult.mclu.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class GuiHUD extends ClickableIcon {
    private static final GuiIcon guiIcon = new GuiIcon(new ResourceLocation(MCLU.MODID, "textures/gui/backpack.png"), 16, 16, 0, 0, 16, 16);
    private static final ClickableIcon clickableIcon = new ClickableIcon(guiIcon, 50, 50);
    private static final GuiCursor guiCursor = ClientEvents.guiCursor;

    public GuiHUD(GuiIcon icon, double x, double y) {
    }

    public static final IGuiOverlay BACKPACK_ICON = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            double mouseX = guiCursor.getMouseX();
            double mouseY = guiCursor.getMouseY();
            float zLevel = 0.0f;
            clickableIcon.render(guiGraphics, mouseX, mouseY, zLevel);
        }
    };
}
