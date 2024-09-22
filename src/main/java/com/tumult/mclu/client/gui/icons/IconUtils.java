package com.tumult.mclu.client.gui.icons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public enum IconUtils {
    INSTANCE;

    private GuiIcons sprite;

    public GuiIcons getIcon() {
        if (INSTANCE.sprite == null) {
            Minecraft minecraft = Minecraft.getInstance();
            TextureManager textureManager = minecraft.getTextureManager();
            SpriteUploader spriteUploader = new SpriteUploader(textureManager);
            INSTANCE.sprite = new GuiIcons(spriteUploader);
        }
        return INSTANCE.sprite;
    }

    private static TextureAtlasSprite getMissingIcon() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(MissingTextureAtlasSprite.getLocation());
    }

    public static void draw(GuiGraphics guiGraphics, double x, double y, float zLevel, @Nonnull TextureAtlasSprite icon, double textureStartX, double textureStartY, double textureEndX, double textureEndY) {
        if (icon == null) {
            icon = getMissingIcon();
        }
        float minU = icon.getU0();
        float maxU = icon.getU1();
        float minV = icon.getV0();
        float maxV = icon.getV1();

        float iconWidthDiv = 1F / icon.contents().width();
        float iconHeightDiv = 1F / icon.contents().height();

        double xOffsetMin = textureStartX * (maxU - minU) * iconWidthDiv; // 0
        double xOffsetMax = textureEndX * (maxU - minU) * iconWidthDiv;
        double yOffsetMin = textureStartY * (maxV - minV) * iconHeightDiv; // 0
        double yOffsetMax = textureEndY * (maxV - minV) * iconHeightDiv;

        innerBlit(guiGraphics.pose().last().pose(), icon, xOffsetMin, xOffsetMax, yOffsetMin, yOffsetMax, zLevel, minU, maxU, minV, maxV);
    }



    public static void innerBlit(Matrix4f matrix4f, TextureAtlasSprite icon, double left, double right, double top, double bottom, float zLevel, float minU, float maxU, float minV, float maxV) {
        RenderSystem.setShaderTexture(0, icon.atlasLocation());
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tess.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, (float) left, (float) bottom, zLevel).uv(minU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) right, (float) bottom, zLevel).uv(maxU, maxV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) right, (float) top, zLevel).uv(maxU, minV).endVertex();
        bufferBuilder.vertex(matrix4f, (float) left, (float) top, zLevel).uv(minU, minV).endVertex();

        BufferUploader.drawWithShader(bufferBuilder.end());
    }
}
