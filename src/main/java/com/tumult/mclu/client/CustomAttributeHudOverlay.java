package com.tumult.mclu.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CustomAttributeHudOverlay {
    private static final ResourceLocation ICONS = new ResourceLocation("minecraft", "textures/gui/icons.png");

    public static final IGuiOverlay CUSTOM_HEALTH_HUD = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            int currentHealth = (int)player.getHealth();
            //int maxHealth = (int)player.getMaxHealth();

            int x = screenWidth / 2 - 92;
            int y = screenHeight - 38;

            Font font = Minecraft.getInstance().font;
            String healthText = String.format("%d", currentHealth);
            int healthTextWidth = font.width(healthText);
            guiGraphics.drawString(Minecraft.getInstance().font,
                    String.format("%d", currentHealth),
                    x - healthTextWidth, y, 0xff1313, false);
        }
    });

    public static final IGuiOverlay CUSTOM_ARMOR_HUD = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ICONS);

        int x = screenWidth / 2 - 91;
        int y = screenHeight - 49;

        Player player = Minecraft.getInstance().player;
        float maxHealth = player.getMaxHealth();
        int maxArmorValue = player.getArmorValue();
        double currentArmorValue = player.getAttributeValue(CustomAttributes.CURRENT_ARMOR.get());

        int healthIcons = Mth.ceil(maxHealth / 2.0F);
        int totalIcons = healthIcons + maxArmorValue;
        int healthRows = Mth.ceil(maxHealth / 20.0F);
        int rowSpacing = Math.max(10 - (healthRows - 2), 3);

        for (int i = 0; i < maxArmorValue; i++) {
            int barX = x + (i % 10) * 8;
            int barY = y - (i / 10) * rowSpacing;

            if (i < currentArmorValue) {
                guiGraphics.blit(ICONS, barX, barY, 88, 18, 9, 9);  // Full armor icon
            } else {
                guiGraphics.blit(ICONS, barX, barY, 70, 18, 9, 9);  // Empty armor icon
            }
        }
        if (maxArmorValue > 0) {
            Font font = Minecraft.getInstance().font;
            String armorText = String.format("%d", (int)currentArmorValue);
            int armorTextWidth = font.width(armorText);
            guiGraphics.drawString(Minecraft.getInstance().font,
                    String.format("%d", (int)currentArmorValue),
                    x - armorTextWidth - 1, y + 1, 0xb8b9c4, false);
        }
    });

    public static final IGuiOverlay IMAGINATION_HUD = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ICONS);

        int x = screenWidth / 2 + 81;
        int y = screenHeight - 39;

        Player player = Minecraft.getInstance().player;
        double max_imagination = player.getAttributeValue(CustomAttributes.MAX_IMAGINATION.get());
        double current_imagination = player.getAttributeValue(CustomAttributes.CURRENT_IMAGINATION.get());
        int imaginationRows = Mth.ceil(max_imagination / 20.0F);
        int rowSpacing = Math.max(10 - (imaginationRows - 2), 3);
        for (int i = 0; i < max_imagination; i++) {
            int barX = x - (i % 10) * 8;
            int barY = y - (i / 10) * rowSpacing;
            if (i < current_imagination) {
                guiGraphics.blit(ICONS, barX + 1, barY, 16, 18, 9, 9);
            } else {
                guiGraphics.blit(ICONS, barX, barY, 16, 27, 9, 9);
            }
        }
        guiGraphics.drawString(Minecraft.getInstance().font,
                String.format("%d", (int)current_imagination),
                x + 5, y + 1, 0x0094ff, false);
    });
}
