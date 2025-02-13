package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import com.tumult.mclu.client.gui.frame.core.geometry.Rect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

import static com.tumult.mclu.client.gui.frame.core.BufferProvider.drawRectWithUV;

public class GuiSprite <T extends IRect> {
    private final ResourceLocation location;
    private final float[] uv;
    private final int len;

    public final T rect;

    public GuiSprite(String name, T rect, int canvasSize) {
        this.location = new ResourceLocation(McluConstants.MOD_ID, "gui/textures/" + name + ".png");
        this.rect = rect;
        this.len = rect.len();

        uv = new float[len];
        rect.normalize(uv, canvasSize);
    }

    public void render(GuiGraphics guiGraphics) {
        ShaderInstance oldShader = RenderSystem.getShader();
        RenderSystem.setShaderTexture(0, location);

        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        drawRectWithUV(
                guiGraphics.pose().last().pose(),
                (Rect) rect, uv
        );
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(() -> oldShader);
    }
}