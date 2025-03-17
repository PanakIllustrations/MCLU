package com.tumult.mclu.client.gui.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.core.geometry.Rect;
import com.tumult.mclu.client.gui.core.util.BufferProvider;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class GuiSprite extends AbstractGuiElement<Rect> {
    private final ResourceLocation loc;
    private final float[] uv;

    public GuiSprite(Rect rect, String name, int canvasSize, Color rgba) {
        super(rect);

        format = DefaultVertexFormat.POSITION_TEX;
        loc = new ResourceLocation(McluConstants.MOD_ID, "gui/textures/" + name + ".png");
        color = new float[]{
                rgba.getRed() / 255f,
                rgba.getGreen() / 255f,
                rgba.getBlue() / 255f,
                rgba.getAlpha() / 255f
        };
        uv = new float[rect.resolution() * 8];
        rect.normalize(canvasSize, uv);
        BufferProvider.getQuadPoints(uv, uv);
    }

    protected void doRender() {
        ShaderInstance oldShader = RenderSystem.getShader();
        updateShaderInstance();

        BufferProvider.drawPointsTex(rect, rect.mode(), format, points, uv, rect.depth());
        RenderSystem.setShader(() -> oldShader);
    }

    void updateShaderInstance() {
        RenderSystem.setShaderTexture(0, loc);
        RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
    }

    public void addToBuffer(BufferBuilder builder) {
        float x = rect.getAbsoluteLeft();
        float y = rect.getAbsoluteTop();

        for (int i = 0; i < points.length; i += 2){
            builder
                    .vertex(points[i] + x, points[i+1] + y, rect.depth())
                    .uv(uv[i], uv[i+1])
                    .endVertex();
        }
    }
}