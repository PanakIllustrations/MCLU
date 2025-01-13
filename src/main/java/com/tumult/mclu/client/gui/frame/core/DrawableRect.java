package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.nio.FloatBuffer;

public class DrawableRect extends Node {

    private final Vector2DPoint TOP_LEFT = new Vector2DPoint(0, -1);
    private final Vector2DPoint BOTTOM_LEFT = new Vector2DPoint(-1, 0);
    private final Vector2DPoint BOTTOM_RIGHT = new Vector2DPoint(0, 1);
    private final Vector2DPoint TOP_RIGHT = new Vector2DPoint(1, 0);

    private final int numVertices = 10;
    protected Color color;
    protected float zLevel = 0;
    protected float radius = 0;
    protected Vector4DRect rectBounds;

    public DrawableRect(Color color, Vector4DRect rect, float radius ) {
        this.rectBounds = rect;
        this.color = color;
        this.radius = radius;
    }
    public void setUL(Vector2DPoint ul) {
        this.rectBounds.setUl(ul);
    }
    public void draw(GuiGraphics guiGraphics) {
        if (!this.isVisible) {
            return;
        }
        FloatBuffer vertices;
        vertices = preDrawRect(BufferUtils.createFloatBuffer(numVertices * 4 * 3)); // curve resolution * 4 corners * 3 dimensions
        preDrawRectColor();
        drawColor(guiGraphics, vertices);
    }

    private FloatBuffer preDrawRect(FloatBuffer vertices) {
        Vector4DRect inner = new Vector4DRect(
            this.rectBounds.left() + radius,
            this.rectBounds.top() + radius,
            this.rectBounds.right() - radius,
            this.rectBounds.bottom() - radius
        );

        addArcPoints(vertices, TOP_LEFT, radius, inner.left(), inner.top());
        addArcPoints(vertices, BOTTOM_LEFT, radius, inner.left(), inner.bottom());
        addArcPoints(vertices, BOTTOM_RIGHT, radius, inner.right(), inner.bottom());
        addArcPoints(vertices, TOP_RIGHT, radius, inner.right(), inner.top());

        vertices.flip();
        return vertices;
    }

    private void addArcPoints(FloatBuffer buffer, Vector2DPoint angle, float radius, double xOffset, double yOffset) {
        final float theta = (float) (Math.PI / 20);
        final float tangent_factor = (float) Math.tan(theta);
        final float radial_factor = (float) Math.cos(theta);
        double x = radius * angle.x();
        double y = radius * angle.y();

        for (int i = 0; i < numVertices; i++) {
            buffer.put((float) (x + xOffset));
            buffer.put((float) (y + yOffset));
            buffer.put( this.zLevel);
            double tx = y;
            double ty = -x;
            x += tx * tangent_factor;
            y += ty * tangent_factor;
            x *= radial_factor;
            y *= radial_factor;
        }
    }

    private float getColor(String color) {
        return switch (color) {
            case "r" -> (float) (this.color.getRed() / 255);
            case "g" -> (float) (this.color.getGreen() / 255);
            case "b" -> (float) (this.color.getBlue() / 255);
            case "a" -> (float) (this.color.getAlpha() / 255);
            default -> 0;
        };
    }

    private void preDrawRectColor() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private void drawColor(GuiGraphics guiGraphics, FloatBuffer vertices) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        addVerticesToBuffer(bufferBuilder, guiGraphics.pose().last().pose(), vertices);
        postDraw(bufferBuilder);
    }

    private void postDraw(BufferBuilder bufferBuilder) {
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }
    private void addVerticesToBuffer(BufferBuilder bufferBuilder, Matrix4f matrix4f, FloatBuffer vertices) {
        vertices.rewind();
        while (vertices.hasRemaining()) {
            bufferBuilder
                .vertex(
                    matrix4f,
                    vertices.get(),
                    vertices.get(),
                    vertices.get())
                .color(
                    getColor("r"),
                    getColor("g"),
                    getColor("b"),
                    getColor("a"))
                .endVertex();
        }
    }
}
