package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.awt.*;
import java.nio.FloatBuffer;

public class DrawableSprite {
    private final ResourceLocation texture;
    private final Vector4DRect textureUV;
    protected Vector4DRect rectBounds;
    protected float zLevel;

    public DrawableSprite(ResourceLocation icon, Vector4DRect rect, Vector4DRect uv, float z) {
        this.rectBounds = rect;
        this.textureUV = uv;
        this.texture = icon;
        this.zLevel = z;
    }
    public void setUL(Vector2DPoint ul) {
        this.rectBounds.setUl(ul);
    }
    public void draw(GuiGraphics guiGraphics, Vector2DPoint screenXY) {
        setUL(screenXY);
        preDrawSprite();
        draw(guiGraphics);
    }

    public void draw(GuiGraphics guiGraphics) {
        preDrawSprite();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0f, 0f, zLevel);
        innerBlit(guiGraphics.pose().last().pose(),
            (float) rectBounds.left(), // screen x offset
            (float) rectBounds.right(), // screen x offset + bounds width
            (float) rectBounds.top(), // screen y offset
            (float) rectBounds.bottom(), // screen y offset + bounds height
                zLevel,
            (float) textureUV.left(), // minU: x texture offset
            (float) textureUV.getWh().x(), // maxU: x texture offset + icon width
            (float) textureUV.top(),  // minV: y texture offset
            (float) textureUV.getWh().y()); // maxV: y texture offset + icon height
        guiGraphics.pose().popPose();
    }
    private void preDrawSprite() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }
    private void innerBlit(Matrix4f matrix4f, float left, float right, float top, float bottom, float zLevel, float minU, float maxU, float minV, float maxV) {
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
}
