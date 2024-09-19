package com.tumult.mclu.client.gui.icons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

/*
blit(ResourceLocation location, int screenPosX, int screenPosY, int zLevel, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconPosX, int iconPosY, int iconWidth, int iconHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconWidth1, int iconHeight1, float iconPosX, float iconPosY, int iconWidth2, int iconHeight2, int textureWidth, int textureHeight);
blit(ResourceLocation location, int left, int right, int top, int bottom, int zLevel, int iconWidth2, int iconHeight2, float iconPosX, float iconPosY, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
*/

public class GuiIcon {
    private final ResourceLocation location;
    private final float iconPosX;
    private final float iconPosY;
    public final float iconWidth;
    public final float iconHeight;
    private final float textureWidth;
    private final float textureHeight;

    public GuiIcon(ResourceLocation location, int iconWidth, int iconHeight, int iconPosX, int iconPosY, int textureWidth, int textureHeight) {
        this.location = location;
        this.iconPosX = iconPosX;
        this.iconPosY = iconPosY;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    public void draw(GuiGraphics guiGraphics, int screenPosX, int screenPosY, float zLevel) {
        ShaderInstance oldShader = RenderSystem.getShader();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(screenPosX, screenPosY, zLevel);

        guiGraphics.blit(location, screenPosX, screenPosY, (int) zLevel ,(int) iconPosX,(int) iconPosY,(int) iconWidth,(int) iconHeight,(int) textureWidth,(int) textureHeight);

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        guiGraphics.pose().popPose();
        RenderSystem.setShader(() -> oldShader);
    }

    public void draw(GuiGraphics guiGraphics, double screenPosX, double screenPosY, float zLevel) {
        ShaderInstance oldShader = RenderSystem.getShader();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(screenPosX, screenPosY, zLevel);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        float minU = iconPosX / textureWidth;
        float maxU = (iconPosX + iconWidth) / textureWidth;
        float minV = iconPosY / textureHeight;
        float maxV = (iconPosY + iconHeight) / textureHeight;

        innerBlit(guiGraphics.pose().last().pose(), screenPosX, screenPosX + iconWidth, screenPosY, screenPosY + iconHeight,zLevel, minU, maxU, minV, maxV);

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        guiGraphics.pose().popPose();
        RenderSystem.setShader(() -> oldShader);
    }

    private static void innerBlit(Matrix4f matrix4f, double left, double right, double top, double bottom, float zLevel, float minU, float maxU, float minV, float maxV) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();

        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, (float) left, (float) bottom, zLevel).uv(minU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) right, (float) bottom, zLevel).uv(maxU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) right, (float) top, zLevel).uv(maxU, minV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) left, (float) top, zLevel).uv(minU, minV).endVertex();

        BufferUploader.drawWithShader(bufferBuilder.end());
    }
}

