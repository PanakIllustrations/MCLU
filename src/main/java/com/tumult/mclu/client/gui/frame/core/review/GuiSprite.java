package com.tumult.mclu.client.gui.frame.core.review;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.core.BufferProvider;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.function.Supplier;

public class GuiSprite extends AbstractGuiElement<IRect> {
    private final ResourceLocation loc;
    private final float[] uv;

    public GuiSprite(IRect rect, String name, int canvasSize, Color rgba) {
        super(rect);

        format = DefaultVertexFormat.POSITION_TEX;
        loc = new ResourceLocation(McluConstants.MOD_ID, "gui/textures/" + name + ".png");
        color = new float[]{
            rgba.getRed() / 255f,
            rgba.getGreen() / 255f,
            rgba.getBlue() / 255f,
            rgba.getAlpha() / 255f
        };
        uv = new float[rect.res() * 8];
        rect.normalize(canvasSize, uv);
        BufferProvider.getQuadPoints(uv, uv);
    }

    @Override
    public void render() {
        ShaderInstance oldShader = RenderSystem.getShader();
        updateShaderInstance();
        BufferProvider.drawPointsTex(rect, rect.mode(), format, points, uv, rect.zLevel());
        RenderSystem.setShader(() -> oldShader);
    }
    @Override
    void updateShaderInstance() {
        RenderSystem.setShaderTexture(0, loc);
        RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
    }
}
