package com.tumult.mclu.client.gui.core.util;

import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.client.gui.core.geometry.IRect;
import com.tumult.mclu.client.gui.core.geometry.Rect;
import com.tumult.mclu.client.gui.core.geometry.RoundRect;

public class BufferProvider {
    private static final float theta = (float) (Math.PI / RenderingConfig.ARC_SEGMENTS);
    private static final float tangent_factor = (float) Math.tan(theta);
    private static final float radial_factor = (float) Math.cos(theta);

    private static final float[] CORNERS = new float[]{
         0,-1, // TOP LEFT
        -1, 0, // BOTTOM LEFT
         0, 1, // BOTTOM RIGHT
         1, 0  // TOP RIGHT
    };

    public static void updatePoints(IRect rect, float[] out) {
        if (rect instanceof RoundRect) {
            updatePoints((RoundRect) rect, out);
        } else {
            updatePoints((Rect) rect, out);
        }
    }

    private static void updatePoints(Rect rect, float[] out) {
        float[] r = new float[8]; rect.copyTo(r);
        getQuadPoints(r, out);
    }

    private static void updatePoints(RoundRect rect, float[] out) {
        float[] inner = new float[8]; rect.copyInnerTo(inner);
        getArcPoints(rect.resolution(), rect.radius(), inner, out);
    }

    public static void getQuadPoints(float[] r, float[] out) {
        float width = r[2];
        float height = r[3];

        out[0] = 0; out[1] = 0; // top left
        out[2] = 0; out[3] = height; // bottom left
        out[4] = width; out[5] = height; // bottom right
        out[6] = width; out[7] = 0; // top right
    }

    public static void getArcPoints(int res, float radius, float[] offset, float[] out) {
        int indexOffset = 0;
        for (int j = 0; j < 8; j += 2) {
            float x = radius * CORNERS[j];
            float y = radius * CORNERS[j + 1];
            for (int i = 0; i < res; i++) {
                int index = indexOffset + (i * 2);
                out[index] = offset[j] + x;
                out[index + 1] = offset[j + 1] + y;
                float tx = y * tangent_factor;
                float ty = -x * tangent_factor;
                x += tx;
                y += ty;
                x *= radial_factor;
                y *= radial_factor;
            }
            indexOffset += res * 2; // Move to next arc segment
        }
    }

    public static void drawPointsCol(Rect rect, VertexFormat.Mode mode, VertexFormat format, float[] points, float[] col, float zLevel){
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(mode, format);

        float x = rect.getAbsoluteLeft();
        float y = rect.getAbsoluteTop();

        for (int i = 0; i < points.length; i += 2){
            builder
                    .vertex(points[i] + x, points[i+1] + y, zLevel)
                    .color(col[0], col[1], col[2], col[3])
                    .endVertex();
        }
        BufferUploader.drawWithShader(builder.end());
    }


    public static void drawPointsTex(Rect rect, VertexFormat.Mode mode, VertexFormat format, float[] points, float[] uv, float zLevel){
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(mode, format);

        float x = rect.getAbsoluteLeft();
        float y = rect.getAbsoluteTop();

        for (int i = 0; i < points.length; i += 2){
            builder
                    .vertex(points[i] + x, points[i+1] + y, zLevel)
                    .uv(uv[i], uv[i+1])
                    .endVertex();
        }
        BufferUploader.drawWithShader(builder.end());
    }

    public static float[] unpackColor(int packed, float[] out) {
        out[0] = ((packed >> 16) & 0xFF) / 255f; // R
        out[1] = ((packed >> 8) & 0xFF) / 255f;  // G
        out[2] = (packed & 0xFF) / 255f;         // B
        out[3] = ((packed >> 24) & 0xFF) / 255f; // A
        return out;
    }
}
