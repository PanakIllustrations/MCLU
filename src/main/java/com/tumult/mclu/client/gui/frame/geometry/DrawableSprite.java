package com.tumult.mclu.client.gui.frame.geometry;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.client.gui.frame.core.Node;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.io.Serializable;

public class DrawableSprite extends Node implements Serializable {
    protected final ResourceLocation texture;      // Changed to protected!
    protected final Vector4DRect textureUV;        // Changed to protected!
    protected Vector4DRect rectBounds;
    protected float zLevel;
    protected boolean debugPrinted = false;

    public DrawableSprite(ResourceLocation icon, Vector4DRect rect, Vector4DRect uv, float z) {
        this.rectBounds = rect;
        this.textureUV = uv;
        this.texture = icon;
        this.zLevel = z;
    }

    public void setUL(Vector2DPoint ul) {
        this.rectBounds.setUl(ul);
    }

    @Override
    public Vector4DRect getBounds() {
        return rectBounds;
    }

    public Vector4DRect getTextureUV(){
        return textureUV;
    }

    public ResourceLocation getTexture(){
        return texture;
    }

    public float getzLevel() {
        return zLevel;
    }

    public void draw(GuiGraphics guiGraphics, Vector2DPoint screenXY) {
        if (!this.isVisible) {
            return;
        }
        setUL(screenXY);
        preDrawSprite();
        draw(guiGraphics);
    }

    @Override
    public void draw(GuiGraphics guiGraphics) {
        if (!this.isVisible) {
            return;
        }
        if (!debugPrinted) {
            printDebugInfo();
            debugPrinted = true;
        }
        preDrawSprite();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0f, 0f, zLevel);
        innerBlit(guiGraphics.pose().last().pose(),
                (float) rectBounds.left(),
                (float) rectBounds.right(),
                (float) rectBounds.top(),
                (float) rectBounds.bottom(),
                zLevel,
                (float) textureUV.left(),
                (float) textureUV.getWh().x(),
                (float) textureUV.top(),
                (float) textureUV.getWh().y());
        guiGraphics.pose().popPose();
    }

    private void preDrawSprite() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private void innerBlit(Matrix4f matrix4f, float left, float right, float top, float bottom,
                           float zLevel, float minU, float maxU, float minV, float maxV) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, left, bottom, zLevel).uv(minU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, right, bottom, zLevel).uv(maxU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, right, top, zLevel).uv(maxU, minV).endVertex();
        bufferBuilder.vertex(matrix4f, left, top, zLevel).uv(minU, minV).endVertex();
        postDrawSprite(bufferBuilder);
    }

    private void postDrawSprite(BufferBuilder bufferBuilder) {
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    public void printDebugInfo() {
        System.out.printf(
                "DrawableSprite[ul=(%.1f,%.1f), br=(%.1f,%.1f), wh=(%.1f,%.1f), path=(%s), visible=%b]%n",
                rectBounds.left(), rectBounds.top(),
                rectBounds.right(), rectBounds.bottom(),
                rectBounds.width(), rectBounds.height(),
                texture.getPath(),
                isVisible
        );
    }
}