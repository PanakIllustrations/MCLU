package com.tumult.mclu.client.gui.frame.core.review;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.McluConstants;
import com.tumult.mclu.client.gui.frame.core.BufferProvider;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class GuiIcon extends IGui<IRect> {
    private enum RenderType { COLOR, TEXTURE_COLOR }
    private final RenderType renderType;
    private final VertexFormat vertexFormat;
    private final ResourceLocation loc;
    private final int res;
    private final int[] col;
    private final float[] uv;
    public GuiIcon(IRect rect, Color color) {
        super(rect);
        renderType = RenderType.COLOR;
        vertexFormat = DefaultVertexFormat.POSITION_COLOR;
        loc = null;
        res = 8 * rect.resolution();
        col = new int[]{
            color.getRed(),
            color.getGreen(),
            color.getBlue(),
            color.getAlpha()
        };
        uv = null;
    }

    public GuiIcon(IRect rect, String name, int canvasSize, Color color) {
        super(rect);
        renderType = RenderType.TEXTURE_COLOR;
        vertexFormat = DefaultVertexFormat.POSITION_TEX_COLOR;
        loc = new ResourceLocation(McluConstants.MOD_ID, "gui/textures/" + name + ".png");
        res = 8 * rect.resolution();
        col = new int[]{
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                color.getAlpha()
        };
        uv = new float[res];
        rect.normalize(canvasSize, uv);
        BufferProvider.getQuadPoints(uv, uv);
    }

    protected void render(BufferBuilder builder) {
        float[] points = new float[res];
        BufferProvider.getPoints(rect, points);
        builder.begin(rect.format(), vertexFormat);
        switch (renderType) {
            case TEXTURE_COLOR:
                RenderSystem.setShaderTexture(0, loc);
                BufferProvider.drawPointsTexColor(builder, points, uv, col, rect.zLevel());
                break;
            case COLOR:
                BufferProvider.drawPointsColor(builder, points, col, rect.zLevel());
                break;
        }
        BufferUploader.drawWithShader(builder.end());
    }
}
