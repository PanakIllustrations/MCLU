package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.client.gui.frame.core.geometry.Rect;
import com.tumult.mclu.client.gui.frame.core.geometry.Stadium;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.awt.*;
import java.nio.FloatBuffer;

public class BufferProvider {
    private static final Vector2f TOP_LEFT = new Vector2f(0, -1);
    private static final Vector2f BOTTOM_LEFT = new Vector2f(-1, 0);
    private static final Vector2f BOTTOM_RIGHT = new Vector2f(0, 1);
    private static final Vector2f TOP_RIGHT = new Vector2f(1, 0);

    public static FloatBuffer getBuffer(Rect r) {
        FloatBuffer buffer = FloatBuffer.allocate(8); // 4 corners * 2 dimensions

        float left = r.left();
        float right = r.right();
        float top = r.top();
        float bottom = r.bottom();

        buffer.put(left); buffer.put(bottom); // bottom left
        buffer.put(right); buffer.put(bottom); // bottom right
        buffer.put(right); buffer.put(top); // top right
        buffer.put(left); buffer.put(top); // top left

        buffer.flip();
        return buffer;
    }
    public static void drawWithBuffers(Matrix4f matrix4f, VertexFormat.Mode mode, VertexFormat format,
                                       FloatBuffer vertexBuffer, FloatBuffer uvBuffer, float zLevel) {
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(mode, format);

        boolean hasUV = (uvBuffer != null);

        while (vertexBuffer.hasRemaining()) {
            float x = vertexBuffer.get();
            float y = vertexBuffer.get();

            builder.vertex(matrix4f, x, y, zLevel);

            if (hasUV && uvBuffer.hasRemaining()) {
                float u = uvBuffer.get();
                float v = uvBuffer.get();
                builder.uv(u, v);
            }

            builder.endVertex();
        }

        BufferUploader.drawWithShader(builder.end());
    }
    public static void drawRectWithUV(Matrix4f matrix4f, Rect rect, float[] uv) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        float left = rect.left();
        float top = rect.top();
        float right = rect.right();
        float bottom = rect.bottom();
        float zLevel = rect.zLevel();

        float minU = uv[0];
        float minV = uv[1];
        float maxU = uv[2];
        float maxV = uv[3];

        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        // bottom left
        builder.vertex(matrix4f, left, bottom, zLevel)
                .uv(minU, maxV)
                .endVertex();

        // bottom right
        builder.vertex(matrix4f, right, bottom, zLevel)
                .uv(maxU, maxV)
                .endVertex();

        // top right
        builder.vertex(matrix4f, right, top, zLevel)
                .uv(maxU, minV)
                .endVertex();

        // top left
        builder.vertex(matrix4f, left, top, zLevel)
                .uv(minU, minV)
                .endVertex();

        BufferUploader.drawWithShader(builder.end());
    }


    public static void drawRectWithUV(Matrix4f matrix4f, Rect rect, float[] uv, Color color) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        float left = rect.left();
        float top = rect.top();
        float right = rect.right();
        float bottom = rect.bottom();
        float zLevel = rect.zLevel();

        float minU = uv[0];
        float minV = uv[1];
        float maxU = uv[2];
        float maxV = uv[3];

        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(rect.format(), DefaultVertexFormat.POSITION_TEX_COLOR);

        // bottom left
        builder.vertex(matrix4f, left, bottom, zLevel)
                .uv(minU, maxV)
                .color(r,g,b,a)
                .endVertex();

        // bottom right
        builder.vertex(matrix4f, right, bottom, zLevel)
                .uv(maxU, maxV)
                .color(r,g,b,a)
                .endVertex();

        // top right
        builder.vertex(matrix4f, right, top, zLevel)
                .uv(maxU, minV)
                .color(r,g,b,a)
                .endVertex();

        // top left
        builder.vertex(matrix4f, left, top, zLevel)
                .uv(minU, minV)
                .color(r,g,b,a)
                .endVertex();

        BufferUploader.drawWithShader(builder.end());
    }

    public static FloatBuffer getBuffer(Stadium r) {
        FloatBuffer buffer = FloatBuffer.allocate(r.resolution() * 8); // corner resolution * 4 corners * 2 dimensions

        float radiusX = r.radiusX();
        float radiusY = r.radiusY();

        float left = r.left() + radiusX;
        float upper = r.top() + radiusY;
        float right = r.right() - radiusX;
        float bottom = r.bottom() - radiusY;

        int resolution = r.resolution();

        addArcPoints(buffer, TOP_LEFT, radiusX, radiusY, left, upper, resolution);
        addArcPoints(buffer, BOTTOM_LEFT, radiusX, radiusY, left, bottom, resolution);
        addArcPoints(buffer, BOTTOM_RIGHT, radiusX, radiusY, right, bottom, resolution);
        addArcPoints(buffer, TOP_RIGHT, radiusX, radiusY, right, upper, resolution);

        buffer.flip();
        return buffer;
    }

    private static void addArcPoints(FloatBuffer buffer, Vector2f angle, float radiusX, float radiusY, float xOffset, float yOffset, int resolution) {
        final float theta = (float) (Math.PI / 20);
        final float tangent_factor = (float) Math.tan(theta);
        final float radial_factor = (float) Math.cos(theta);
        float x = radiusX * angle.x();
        float y = radiusY * angle.y();

        for (int i = 0; i < resolution; i++) {
            buffer.put(x + xOffset);
            buffer.put(y + yOffset);
            float tx = y;
            float ty = -x;
            x += tx * tangent_factor;
            y += ty * tangent_factor;
            x *= radial_factor;
            y *= radial_factor;
        }
    }

    public static void drawVertices(BufferBuilder builder, Color color, float zLevel, Matrix4f matrix4f, FloatBuffer buffer) {
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;

        while (buffer.hasRemaining()) {
            builder
                    .vertex(matrix4f, buffer.get(), buffer.get(), zLevel)
                    .color(r, g, b, a)
                    .uv(buffer.get(), buffer.get())
                    .endVertex();
        }

        buffer.flip();
        BufferUploader.drawWithShader(builder.end());
    }

    public static Vector2f defaultCanvasSize(Vector2f dimensions) {
        int intValue = (int) Math.ceil(Math.max(dimensions.x(), dimensions.y()));
        intValue--;
        intValue |= intValue >> 1;
        intValue |= intValue >> 2;
        intValue |= intValue >> 4;
        intValue |= intValue >> 8;
        intValue |= intValue >> 16;
        return new Vector2f(intValue + 1);
    }
}
