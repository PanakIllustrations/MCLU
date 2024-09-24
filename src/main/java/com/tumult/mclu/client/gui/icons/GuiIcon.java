package com.tumult.mclu.client.gui.icons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.ClickableIcon;
import com.tumult.mclu.client.gui.geometry.Vector2DPoint;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.awt.*;

/*
blit(ResourceLocation location, int screenPosX, int screenPosY, int zLevel, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconPosX, int iconPosY, int iconWidth, int iconHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, int iconWidth1, int iconHeight1, float iconPosX, float iconPosY, int iconWidth2, int iconHeight2, int textureWidth, int textureHeight);
blit(ResourceLocation location, int left, int right, int top, int bottom, int zLevel, int iconWidth2, int iconHeight2, float iconPosX, float iconPosY, int textureWidth, int textureHeight);
blit(ResourceLocation location, int screenPosX, int screenPosY, float iconPosX, float iconPosY, int iconWidth, int iconHeight, int textureWidth, int textureHeight);
*/

public class GuiIcon {
    public final DrawableIcon backpack;
    public final DrawableIcon cursor;
    public final DrawableIcon map;
    public final DrawableIcon passport;

    public GuiIcon() {
        this.backpack = registerIcon("backpack", 16, 16, 16, 16);
        this.cursor = registerIcon("mouse_cursor", 9, 17, 32, 32);
        this.map = registerIcon("map", 16, 16, 16, 16);
        this.passport = registerIcon("passport", 16, 16, 16, 16);
    }

    private DrawableIcon registerIcon(String name, int iconWidth, int iconHeight, int textureWidth, int textureHeight) {
        ResourceLocation location = new ResourceLocation(McluConstants.MOD_ID, "/textures/gui/" + name + ".png");
        return new DrawableIcon(location, iconWidth, iconHeight, textureWidth, textureHeight);
    }

    public static class DrawableIcon {

        final ResourceLocation location;
        private final int textureWidth;
        private final int textureHeight;
        private int iconWidth;
        private int iconHeight;
        private int textureStartX;
        private int textureStartY;
        private Color color;
        private float zLevel;

        protected DrawableIcon(ResourceLocation locationIn, int iconWidth, int iconHeight, int textureWidth, int textureHeight) {
            this.location = locationIn;
            this.iconWidth = iconWidth;
            this.iconHeight = iconHeight;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
            this.textureStartX = 0;
            this.textureStartY = 0;
            this.color = Color.WHITE;
            this.zLevel = 0f;
        }

        public int getIconWidth() {
            return iconWidth;
        }
        public int getIconHeight() {
            return iconHeight;
        }
        public int getTextureWidth() {
            return textureWidth;
        }
        public int getTextureHeight() {
            return textureHeight;
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

        public void setTextureOrigin(Vector2DPoint origin) {
            setTextureOrigin((int)origin.x, (int)origin.y);
        }

        public void setTextureOrigin(int textureX, int textureY) {
            setTextureStartX(textureX);
            setTextureStartY(textureY);
        }

        public void setTextureStartX(int textureX) {
            this.textureStartX = textureX;
        }

        public void setTextureStartY(int textureY) {
            this.textureStartY = textureY;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setZLevel(float zLevel) {
            this.zLevel = zLevel;
        }

        public void draw(GuiGraphics guiGraphics, Vector2DPoint point) {
            draw(guiGraphics, point.x, point.y);
        }

        public void draw(GuiGraphics guiGraphics, double screenX, double screenY) {
            preDraw(guiGraphics);
            innerDraw(guiGraphics, screenX, screenY);
            postDraw(guiGraphics);
        }

        public void innerDraw(GuiGraphics guiGraphics, double screenX, double screenY) {

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            float minU = (float) ((textureStartX) / textureWidth);
            float maxU = (float) ((textureStartX + iconWidth) / textureWidth);
            float minV = (float) ((textureStartY) / textureHeight);
            float maxV = (float) ((textureStartY + iconHeight) / textureHeight);

            innerBlit(guiGraphics.pose().last().pose(),
                    screenX, screenX + textureWidth, screenY, screenY + textureHeight, zLevel,
                    minU, maxU, minV, maxV
            );
        }

        public void preDraw(GuiGraphics guiGraphics) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 0, zLevel);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, location);
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


/*

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



    */

