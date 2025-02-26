package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.tumult.mclu.client.gui.frame.core.css.Style;
import com.tumult.mclu.client.gui.frame.core.geometry.RoundRect;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;

public class GuiElement extends AbstractGuiElement<RoundRect> {
    private Style style;
    private float[] colorArray = new float[4];

    public GuiElement(RoundRect rect, Style style) {
        super(rect);
        this.style = style;
        this.format = DefaultVertexFormat.POSITION_COLOR;
        applyStyle();
    }

    public void setStyle(Style style) {
        this.style = style;
        applyStyle();
    }

    private void applyStyle() {
        if (style == null) return;

        // Apply geometry properties
        rect.setWH(style.getRect()[Style.WIDTH], style.getRect()[Style.HEIGHT]);
        rect.setR(style.getRadius());
        rect.setDepth(style.getDepth());

        // Apply color
        colorArray = new float[]{
                style.getBackgroundColor().getRed() / 255f,
                style.getBackgroundColor().getGreen() / 255f,
                style.getBackgroundColor().getBlue() / 255f,
                style.getBackgroundColor().getAlpha() / 255f
        };

        // Mark as needing update
        rect.markDirty();
    }

    @Override
    protected void doRender() {
        // If style has changed, reapply it
        if (style.isDirty()) {
            applyStyle();
            style.clearDirty();
        }

        // Update points if geometry has changed
        if (rect.isDirty()) {
            BufferProvider.updatePoints(rect, points);
            rect.clearDirty();
        }

        // Perform the actual rendering
        ShaderInstance oldShader = RenderSystem.getShader();
        updateShaderInstance();
        BufferProvider.drawPointsCol(rect, rect.mode(), format, points, colorArray, rect.depth());
        RenderSystem.setShader(() -> oldShader);
    }

    @Override
    void updateShaderInstance() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(colorArray[0], colorArray[1], colorArray[2], colorArray[3]);
    }

    @Override
    public void addToBuffer(BufferBuilder builder) {
        float x = rect.getAbsoluteLeft();
        float y = rect.getAbsoluteTop();

        for (int i = 0; i < points.length; i += 2){
            builder
                    .vertex(points[i] + x, points[i+1] + y, rect.depth())
                    .color(colorArray[0], colorArray[1], colorArray[2], colorArray[3])
                    .endVertex();
        }
    }
}