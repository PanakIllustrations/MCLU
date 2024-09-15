package com.tumult.mclu.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.MCLU;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Matrix4f;

import java.awt.*;

/*
blit(ResourceLocation location, int screenPosX, int screenPosY, int zLevel, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconPosX, int iconPosY, int iconWidth, int iconHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconWidth1, int iconHeight1, float iconPosX, float iconPosY, int iconWidth2, int iconHeight2, int textureWidth, int textureHeight);
blit(ResourceLocation location, int left, int right, int top, int bottom, int zLevel, int iconWidth2, int iconHeight2, float iconPosX, float iconPosY, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
*/

public class GuiIcon {
    ResourceLocation location;
    private final int screenPosX;
    private final int screenPosY;
    private final int zLevel;
    private final int iconPosX;
    private final int iconPosY;
    private final int iconWidth;
    private final int iconHeight;

    public GuiIcon(ResourceLocation location, int iconWidth, int iconHeight, int iconPosX, int iconPosY, int zLevel, int screenPosX, int screenPosY) {
        this.location = location;
        this.screenPosX = screenPosX;
        this.screenPosY = screenPosY;
        this.zLevel = zLevel;
        this.iconPosX = iconPosX;
        this.iconPosY = iconPosY;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
    }

    public void draw(GuiGraphics guiGraphics,  double mouseX, double mouseY, float partialTicks) {
        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();
        guiGraphics.blit(location, screenPosX, screenPosY, zLevel, (float) iconPosX, (float) iconPosY, iconWidth, iconHeight, 256, 256);
        RenderSystem.disableBlend();
        RenderSystem.setShader(() -> oldShader);
    }

    private static final ResourceLocation texture = new ResourceLocation(MCLU.MODID, "textures/gui/example_menu.png");
    private static final GuiIcon menu = new GuiIcon(texture, 176, 166, 0, 0, 0, 50, 50);

    public static final IGuiOverlay MENU = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            menu.draw(guiGraphics, 0, 0, partialTick);
        }
    };
}