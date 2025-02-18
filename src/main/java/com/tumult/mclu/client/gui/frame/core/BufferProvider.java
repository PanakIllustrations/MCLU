package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;
import com.tumult.mclu.client.gui.frame.core.geometry.RoundRect;

public class BufferProvider {
    private static final float[] CORNERS = new float[]{0,-1, -1,0, 0,1, 1,0};
    private static final float theta = (float) (Math.PI / 20);
    private static final float tangent_factor = (float) Math.tan(theta);
    private static final float radial_factor = (float) Math.cos(theta);

    public static void getPoints(IRect rect, float[] out) {
        float[] r = new float[8]; rect.copy(r);
        getQuadPoints(r, out);
    }

    public static void getPoints(RoundRect stadium, float[] out) {
        float[] s = new float[8 * stadium.resolution()]; stadium.copyInner(s);
        getArcPoints(s, out, stadium.resolution());
    }

    public static void getQuadPoints(float[] r, float[] out) {
        float left = r[0];
        float top = r[1];
        float right = r[2] + left;
        float bottom = r[3] + top;

        out[0] = left; out[1] = bottom; // bottom left
        out[2] = right; out[3] = bottom; // bottom right
        out[4] = right; out[5] = top; // top right
        out[6] = left; out[7] = top; // top left
    }

    public static void getArcPoints(float[] s, float[] out, int res) {
        for (int i = 0; i < 8; i += 2) {
            float x = s[5] * CORNERS[i];
            float y = s[6] * CORNERS[i + 1];
            int baseIndex = i * res;
            for (int j = 0; j < res; j += 2) {
                int index = baseIndex + j;
                out[index] = x + s[i];
                out[index + 1] = y + s[i + 1];

                float tx = y;
                float ty = -x;
                x += tx * tangent_factor;
                y += ty * tangent_factor;
                x *= radial_factor;
                y *= radial_factor;
            }
        }
    }


    public static void drawPointsColor(BufferBuilder builder, float[] points, int[] color, float zLevel){
        for (int i = 0; i < points.length; i += 2){
            builder
                .vertex(points[i], points[i+1], zLevel)
                .color(color[0], color[1], color[2], color[3])
                .endVertex();
        }
    }

    public static void drawPointsTexColor(BufferBuilder builder, float[] points,  float[] uv, int[] color, float zLevel){
        for (int i = 0; i < points.length; i += 2){
            builder
                .vertex(points[i], points[i+1], zLevel)
                .uv(uv[i], uv[i+1])
                .color(color[0], color[1], color[2], color[3])
                .endVertex();
        }
    }
}
