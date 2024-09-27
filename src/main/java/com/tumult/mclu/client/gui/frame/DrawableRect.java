package com.tumult.mclu.client.gui.frame;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector2d;

import java.awt.*;

public class DrawableRect {
    private final ResourceLocation resourceLocation;
    private final Vector2d textureDimensions;
    private Vector2d textureOffset;

    private float zLevel = 0;
    private Color color = Color.WHITE;

    public DrawableRect(ResourceLocation resourceLocation, Vector2d iconDimensions, Vector2d textureDimensions) {
        super(iconDimensions);
        this.resourceLocation = resourceLocation;
        this.textureDimensions = textureDimensions;
        this.textureOffset = new Vector2d(0, 0);
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setZLevel(float zLevel) {
        this.zLevel = zLevel;
    }
    public void setOffset(Vector2d offset) {
        this.textureOffset = offset;
    }

    public void draw(GuiGraphics guiGraphics, Vector2d position) {
        this.moveTo(position);
        draw(guiGraphics);
    }

    public void draw(GuiGraphics guiGraphics) {
        preDraw(guiGraphics);
        innerDraw(guiGraphics);
        postDraw(guiGraphics);
    }

    public void preDraw(GuiGraphics guiGraphics) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, zLevel);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, resourceLocation);
        RenderSystem.setShaderColor(
                (float) color.getRed() / 255f,
                (float) color.getGreen() / 255f,
                (float) color.getBlue() / 255f,
                (float) color.getAlpha() / 255f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    public void postDraw(GuiGraphics guiGraphics) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.pose().popPose();
    }
    private void innerDraw(GuiGraphics guiGraphics) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        Rectangle screenXY = this;
        innerBlit(guiGraphics.pose().last().pose(), screenXY);
    }
    private void innerBlit(Matrix4f matrix4f, Rectangle screenXY) {
        Rectangle textureUV = new Rectangle(textureOffset.x, textureOffset.y, this.getWidth(), this.getHeight()).normalize(textureDimensions);
        innerBlit(matrix4f,
                (float) screenXY.getLeft(), (float) screenXY.getRight(),
                (float) screenXY.getTop(), (float) screenXY.getBottom(), zLevel,
                (float) textureUV.getLeft(), (float) textureUV.getRight(),
                (float) textureUV.getTop(), (float) textureUV.getBottom()
        );
    }

    private static void innerBlit(Matrix4f matrix4f, float left, float right, float top, float bottom, float zLevel, float minU, float maxU, float minV, float maxV) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, left, bottom, zLevel).uv(minU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, right, bottom, zLevel).uv(maxU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, right, top, zLevel).uv(maxU, minV).endVertex();
        bufferBuilder.vertex(matrix4f, left, top, zLevel).uv(minU, minV).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }
}