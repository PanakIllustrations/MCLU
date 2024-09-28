package com.tumult.mclu.client.gui.frame.geometry;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.awt.*;

public class DrawableRect extends Vector4DRect {
    private final ResourceLocation resourceLocation;
    private final Vector2DPoint textureDimensions;
    private Vector2DPoint textureOffset;

    private float zLevel = 0;
    private Color color = Color.WHITE;

    public DrawableRect(ResourceLocation resourceLocation, Vector2DPoint iconDimensions, Vector2DPoint textureDimensions) {
        super(new Vector2DPoint(), iconDimensions);
        this.resourceLocation = resourceLocation;
        this.textureDimensions = textureDimensions;
        this.textureOffset = new Vector2DPoint(0, 0);
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void setZLevel(float zLevel) {
        this.zLevel = zLevel;
    }
    public void setOffset(Vector2DPoint offset) {
        this.textureOffset = offset;
    }


    public void draw(GuiGraphics guiGraphics, Vector2DPoint position) {
        this.ul.set(position.x, position.y);
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
        Vector4DRect screenXY = this;
        innerBlit(guiGraphics.pose().last().pose(), screenXY);
    }
    private void innerBlit(Matrix4f matrix4f, Vector4DRect screenXY) {
        Vector4DRect textureUV = new Vector4DRect(textureOffset.x, textureOffset.y, this.width(), this.height()).normalize(textureDimensions);
        innerBlit(matrix4f,
                (float) screenXY.left(), (float) screenXY.right(),
                (float) screenXY.top(), (float) screenXY.bottom(), zLevel,
                (float) textureUV.left(), (float) textureUV.right(),
                (float) textureUV.top(), (float) textureUV.bottom()
        );
    }

    public static void innerBlit(Matrix4f matrix4f, float left, float right, float top, float bottom, float zLevel, float minU, float maxU, float minV, float maxV) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, left, bottom, zLevel).uv(minU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, right, bottom, zLevel).uv(maxU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, right, top, zLevel).uv(maxU, minV).endVertex();
        bufferBuilder.vertex(matrix4f, left, top, zLevel).uv(minU, minV).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    /**
     * @param frame position and area of the image
     * @param padding the margin that contains the edge and corners
     */
    public void drawNineSliced(GuiGraphics guiGraphics, Vector4DRect frame, Vector4DRect padding) {
        preDraw(guiGraphics);
        drawNineSliced(resourceLocation, guiGraphics,
                frame.ul.x, frame.ul.y, frame.wh.x, frame.wh.y,
                padding.left(), padding.top(), padding.right(), padding.bottom(),
                this.textureDimensions.x, this.textureDimensions.y,
                this.textureOffset.x, this.textureOffset.y

        );
        postDraw(guiGraphics);
    }

    public static void drawNineSliced(ResourceLocation location, GuiGraphics guiGraphics,
                                      double frameX, double frameY, double frameWidth, double frameHeight,
                                      double paddingLeft, double paddingTop, double paddingRight, double paddingBottom,
                                      double textureDimensionX, double textureDimensionY, double textureOffsetX, double textureOffsetY) {
        guiGraphics.blitNineSliced(location,
                (int) frameX, (int) frameY,
                (int) frameWidth, (int) frameHeight,
                (int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom,
                (int) textureDimensionX, (int) textureDimensionY,
                (int) textureOffsetX, (int) textureOffsetY);
    }
}