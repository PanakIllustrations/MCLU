package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.tumult.mclu.client.gui.frame.core.geometry.Rect;
import com.tumult.mclu.client.gui.frame.core.geometry.RoundRect;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;

import java.awt.*;


public class GuiShape extends AbstractGuiElement<Rect> {
    public GuiShape(RoundRect rect, Color rgba) {
        super(rect);
        format = DefaultVertexFormat.POSITION_COLOR;
        color = new float[]{
                rgba.getRed() / 255f,
                rgba.getGreen() / 255f,
                rgba.getBlue() / 255f,
                rgba.getAlpha() / 255f
        };
    }

    @Override
    protected void doRender() {
        ShaderInstance oldShader = RenderSystem.getShader();
        updateShaderInstance();
        BufferProvider.drawPointsCol(rect, rect.mode(), format, points, color, rect.depth());
        RenderSystem.setShader(() -> oldShader);
    }

    @Override
    void updateShaderInstance() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
    }

    @Override
    public void addToBuffer(BufferBuilder builder) {
        float x = rect.getAbsoluteLeft();
        float y = rect.getAbsoluteTop();

        for (int i = 0; i < points.length; i += 2){
            builder
                    .vertex(points[i] + x, points[i+1] + y, rect.depth())
                    .color(color[0], color[1], color[2], color[3])
                    .endVertex();
        }
    }
}