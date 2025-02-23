package com.tumult.mclu.client.gui.frame.core.review;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.tumult.mclu.client.gui.frame.core.BufferProvider;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import com.tumult.mclu.client.gui.frame.core.geometry.RoundRect;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import java.awt.*;

public class GuiShape extends AbstractGuiElement<IRect> {
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
    public void render(){
        ShaderInstance oldShader = RenderSystem.getShader();
        updateShaderInstance();
        BufferProvider.drawPointsCol(rect, rect.mode(), format, points, color, rect.zLevel());
        RenderSystem.setShader(() -> oldShader);
    }

    @Override
    void updateShaderInstance() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
    }
}
