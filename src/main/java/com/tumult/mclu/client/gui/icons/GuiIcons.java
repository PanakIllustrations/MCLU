package com.tumult.mclu.client.gui.icons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.McluConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;



public class GuiIcons {
    public final SpriteUploader spriteUploader;
    public final DrawableSprite mouseCursor;
    public final DrawableSprite backpack;

    public GuiIcons(SpriteUploader spriteUploader) {
        this.spriteUploader = spriteUploader;
        this.mouseCursor = registerIcon("mouse_cursor", 9, 17);
        this.backpack = registerIcon("backpack", 16, 16);
    }

    private DrawableSprite registerIcon(String name, int width, int height) {
        ResourceLocation location = new ResourceLocation(McluConstants.MOD_ID, name);
        return new DrawableSprite(location, width, height, spriteUploader);
    }

    public SpriteUploader getSpriteUploader() {
        return spriteUploader;
    }

    public class DrawableSprite {

        final ResourceLocation location;
        private final int width;
        private final int height;
        private final SpriteUploader spriteUploader;

        protected DrawableSprite(ResourceLocation locationIn, int iconWidth, int iconHeight, SpriteUploader spriteUploader) {
            this.location = locationIn;
            this.width = iconWidth;
            this.height = iconHeight;
            this.spriteUploader = spriteUploader;
        }

        public TextureAtlasSprite getSprite() {
            return spriteUploader.getSprite(location);
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;

        }

        public void draw(GuiGraphics guiGraphics, double screenPosX, double screenPosY, float zLevel) {
            draw(guiGraphics, screenPosX, screenPosY, zLevel, 0, 0, 0, 0);
        }

        public void draw(GuiGraphics guiGraphics, double xOffset, double yOffset, float zLevel, double maskTop, double maskBottom, double maskLeft, double maskRight) {
            double textureWidth = this.width;
            double textureHeight = this.height;

            Minecraft.getInstance().getTextureManager().bindForSetup(spriteUploader.getAtlas().location());
            TextureAtlasSprite icon = spriteUploader.getSprite(location);

            double left = xOffset + maskLeft;
            double top = yOffset + maskTop;
            double right = xOffset - maskRight - maskLeft;
            double bottom = yOffset - maskBottom - maskTop;

            double uSize = icon.getU1() - icon.getU0();
            double vSize = icon.getV1() - icon.getV0();

            float minU = (float) (icon.getU0() + uSize * (maskLeft / textureWidth));
            float minV = (float) (icon.getV0() + vSize * (maskTop / textureHeight));
            float maxU = (float) (icon.getU1() - uSize * (maskRight / textureWidth));
            float maxV = (float) (icon.getV1() - vSize * (maskBottom / textureHeight));

            ShaderInstance oldShader = RenderSystem.getShader();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 0, zLevel);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, McluConstants.LOCATION_GUI_TEXTURE_ATLAS);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            innerBlit(guiGraphics.pose().last().pose(),
                    left, right, top, bottom, zLevel,
                    minU, maxU, minV, maxV);

            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            guiGraphics.pose().popPose();
            RenderSystem.setShader(() -> oldShader);
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

