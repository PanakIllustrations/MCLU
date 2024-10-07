package com.tumult.mclu.client.gui.frame.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.awt.*;
import java.nio.FloatBuffer;
import java.util.List;

public class DrawableRect  {
    private final Vector2DPoint TOP_LEFT = new Vector2DPoint(0, -1);
    private final Vector2DPoint BOTTOM_LEFT = new Vector2DPoint(-1, 0);
    private final Vector2DPoint BOTTOM_RIGHT = new Vector2DPoint(0, 1);
    private final Vector2DPoint TOP_RIGHT = new Vector2DPoint(1, 0);
    private final int numVertices = 10;
    float zLevel = 0;

    protected ResourceLocation resource = null;
    public Vector4DRect bounds, uvMap = null;
    protected Color color = null;
    protected float radius = 0;

    public DrawableRect( ResourceLocation resource, Vector4DRect bounds,  Vector4DRect texture) {
        this.resource = resource;
        this.bounds = bounds;
        this.uvMap = texture;
    }
    public DrawableRect(Vector4DRect bounds, Color color, float radius) {
        this.bounds = bounds;
        this.color = color;
        this.radius = radius;
    }

    public void draw(GuiGraphics guiGraphics) {
        FloatBuffer vertices;
        if (this.resource != null) {
            vertices = BufferUtils.createFloatBuffer(numVertices * 12);
            preDrawSprite();
            drawTexture(guiGraphics, vertices);
        } else {
            vertices = preDrawRect(BufferUtils.createFloatBuffer(numVertices * 12));
            preDrawRectColor();
            drawColor(guiGraphics, vertices);
        }
    }

    public FloatBuffer preDrawRect(FloatBuffer vertices) {
        Vector4DRect inner = new Vector4DRect(
                this.bounds.left() + radius,
                this.bounds.top() + radius,
                this.bounds.right() - radius,
                this.bounds.bottom() - radius
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
            buffer.put((float) this.zLevel);
            double tx = y;
            double ty = -x;
            x += tx * tangent_factor;
            y += ty * tangent_factor;
            x *= radial_factor;
            y *= radial_factor;
        }
    }

    public float getColor(String color) {
        return switch (color) {
            case "r" -> (float) (this.color.getRed() / 255);
            case "g" -> (float) (this.color.getGreen() / 255);
            case "b" -> (float) (this.color.getBlue() / 255);
            case "a" -> (float) (this.color.getAlpha() / 255);
            default -> 0;
        };
    }

    public void preDrawRectColor() {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(
                getColor("r"),
                getColor("g"),
                getColor("b"),
                getColor("a")
        );
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    public void preDrawSprite() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, this.resource);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private void drawColor(GuiGraphics guiGraphics, FloatBuffer vertices) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        addVerticesToBuffer(bufferBuilder, guiGraphics.pose().last().pose(), vertices, null);
        postDraw(bufferBuilder);
    }

    private void drawTexture(GuiGraphics guiGraphics, FloatBuffer vertices) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        addVerticesToBuffer(bufferBuilder, guiGraphics.pose().last().pose(), vertices, createUVBuffer());
        postDraw(bufferBuilder);
    }

    private void addVerticesToBuffer(BufferBuilder bufferBuilder, Matrix4f matrix4f, FloatBuffer vertices, FloatBuffer uv) {
        vertices.rewind();
        while (vertices.hasRemaining()) {
            float x = vertices.get();
            float y = vertices.get();
            float z = vertices.get();

            VertexConsumer vertexConsumer = bufferBuilder.vertex(matrix4f, x, y, z);

            if (uv != null) {
                float u = uv.get();
                float v = uv.get();
                vertexConsumer.uv(u, v);
            } else {
                vertexConsumer.color(
                        getColor("r"),
                        getColor("g"),
                        getColor("b"),
                        getColor("a") // Control alpha transparency here
                );
            }
            vertexConsumer.endVertex();
        }
    }

    private FloatBuffer createUVBuffer() {
        Vector4DRect textureUV = new Vector4DRect(uvMap.left(), uvMap.top(), uvMap.width(), uvMap.height()).normalize(uvMap.getWh());
        FloatBuffer uvBuffer = FloatBuffer.allocate(4);
        uvBuffer.put(new float[]{
                (float) textureUV.left(),    // minU
                (float) textureUV.right(),   // maxU
                (float) textureUV.top(),      // minV
                (float) textureUV.bottom()    // maxV
        });
        uvBuffer.flip();
        return uvBuffer;
    }

    public void postDraw(BufferBuilder buffer) {
        BufferUploader.drawWithShader(buffer.end());
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f); // Reset color to opaque white
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }
}
