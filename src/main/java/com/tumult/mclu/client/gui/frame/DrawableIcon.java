package com.tumult.mclu.client.gui.frame;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.client.gui.geometry.Vector2DPoint;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.awt.*;

public class DrawableIcon implements IDrawable {
    private final ResourceLocation texture;
    private final Vector2DPoint textureDimensions;

    private final Vector2DPoint textureSize;
    private int iconWidth;

    private Vector2DPoint textureStartPoint;

    private float zLevel = 0;
    private Color color = Color.WHITE;

    public DrawableIcon(ResourceLocation resourceLocation, int iconWidth, int iconHeight, int textureWidth, int textureHeight) {
        this.textureStartPoint = new Vector2DPoint(0, 0);
        this.textureDimensions = new Vector2DPoint(textureWidth, textureHeight);
        this.texture = resourceLocation;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;

    }
    public int getIconWidth() {
        return iconWidth;
    }
    public int getIconHeight() {
        return iconHeight;
    }
    public double getTextureWidth() {
        return textureDimensions.x;
    }
    public double getTextureHeight() {
        return textureDimensions.y;
    }

    public void setIconDimensions(Vector2DPoint size) {
        setIconDimensions((int)size.x, (int)size.y);
    }

    public void setIconDimensions(int width, int height) {
        setIconWidth(width);
        setIconHeight(height);
    }

    public void setIconWidth(int width) {
        this.iconWidth = width;
    }

    public void setIconHeight(int height) {
        this.iconHeight = height;
    }

    public void setTexturePosition(Vector2DPoint position) {
        this.textureStartPoint = position;
    }
    public void setTexturePosition(int x, int y) {
        this.textureStartPoint = new Vector2DPoint(x, y);
    }


    public void setColor(Color color) {
        this.color = color;
    }

    public void setZLevel(float zLevel) {
        this.zLevel = zLevel;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, Vector2DPoint position) {
        preDraw(guiGraphics);
        innerDraw(guiGraphics, position);
        postDraw(guiGraphics);
    }

    @Override
    public void preDraw(GuiGraphics guiGraphics) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, zLevel);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(
                (float) color.getRed() / 255f,
                (float) color.getGreen() / 255f,
                (float) color.getBlue() / 255f,
                (float) color.getAlpha() / 255f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    @Override
    public void postDraw(GuiGraphics guiGraphics) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.pose().popPose();
    }

    private void innerDraw(GuiGraphics guiGraphics, Vector2DPoint position) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        float minU = (float) ((textureStartPoint.x) / textureWidth);
        float maxU = (float) ((textureStartPoint.x + iconWidth) / textureWidth);
        float minV = (float) ((textureStartPoint.y) / textureHeight);
        float maxV = (float) ((textureStartPoint.y + iconHeight) / textureHeight);

        innerBlit(guiGraphics.pose().last().pose(),
                position.x, position.x + textureWidth, position.y, position.y + textureHeight, zLevel,
                minU, maxU, minV, maxV
        );
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