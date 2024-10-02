package com.tumult.mclu.client.gui.frame.geometry;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class ScalableRect extends Vector4DRect {
    private final int numVertices = 10;
    private float radius, zLevel;
    private Vector2DPoint widthRange = new Vector2DPoint(20, 40);
    private Vector2DPoint heightRange = new Vector2DPoint(20, 60);

    private final Vector2DPoint TOP_LEFT = new Vector2DPoint(0, -1);
    private final Vector2DPoint BOTTOM_LEFT = new Vector2DPoint(-1, 0);
    private final Vector2DPoint BOTTOM_RIGHT = new Vector2DPoint(0, 1);
    private final Vector2DPoint TOP_RIGHT = new Vector2DPoint(1, 0);

    public ScalableRect(double x, double y, double w, double h, float r) {
        super(x, y, w, h);
        this.radius = r;
    }

    public ScalableRect(double x, double y, double w, double h, float r, Vector2DPoint widthRange, Vector2DPoint heightRange) {
        super(x, y, w, h);
        this.widthRange = widthRange;
        this.heightRange = heightRange;
        this.radius = r;
    }

    public void setR(float r) {
        this.radius = r;
    }
    public void setZ(float z) {
        this.zLevel = z;
    }
    public float getZLevel() {return zLevel;}
    public float getRadius() {return radius;}
    public FloatBuffer preDrawRoundRect() {
        float innerLeft = (float) (this.left() + radius);
        float innerRight = (float) (this.right() - radius);
        float innerTop = (float) (this.top() + radius);
        float innerBottom = (float) (this.bottom() - radius);

        FloatBuffer corner = getArcPoints(TOP_LEFT, radius, innerLeft, innerTop);
        FloatBuffer vertices = BufferUtils.createFloatBuffer(corner.limit() * 4);
        vertices.put(corner);

        corner = getArcPoints(BOTTOM_LEFT, radius, innerLeft, innerBottom);
        vertices.put(corner);

        corner = getArcPoints(BOTTOM_RIGHT, radius, innerRight, innerBottom);
        vertices.put(corner);

        corner = getArcPoints(TOP_RIGHT, radius, innerRight, innerTop);
        vertices.put(corner);
        vertices.flip();
        return vertices;
    }
    private FloatBuffer getArcPoints(Vector2DPoint angle, float radius, float xOffset, float yOffset) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(numVertices * 3);
        final float theta = 0.157f;
        final float rf = 0.988f;

        float x = (float) (radius * angle.x());
        float y = (float) (radius * angle.y());
        float tx, ty;

        for (int i = 0; i < numVertices; i++) {
            buffer.put(x + xOffset);
            buffer.put(y + yOffset);
            buffer.put(zLevel);
            tx = y;
            ty = -x;
            x += tx * theta;
            y += ty * theta;
            x *= rf;
            y *= rf;
        }
        buffer.flip();

        return buffer;
    }

    public void move(double x, double y, double screenWidth, double screenHeight, boolean button) {
        if (button) {
            double clampedX = Math.max(0, Math.min(x, screenWidth - this.getWh().x));
            double clampedY = Math.max(0, Math.min(y, screenHeight - this.getWh().y));
            this.setUl(clampedX, clampedY);
        }
    }
    public void scale(double clampedX, double clampedY, boolean button) {
        if (button) {
            double w = Math.max(widthRange.x, Math.min(widthRange.y, clampedX- this.ul.x));
            double h = Math.max(heightRange.x, Math.min(heightRange.y, clampedY - this.ul.y));
            this.setWh(w, h);
        }
    }


    public void draw(GuiGraphics guiGraphics) {
        preDraw(guiGraphics);
        FloatBuffer vertices = preDrawRoundRect();
        drawBuffer(guiGraphics, vertices);
    }

    public void preDraw(GuiGraphics guiGraphics) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, this.zLevel);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    public void postDraw(GuiGraphics guiGraphics,BufferBuilder buffer) {
        BufferUploader.drawWithShader(buffer.end());
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        guiGraphics.pose().popPose();
    }


    private void drawBuffer(GuiGraphics guiGraphics, FloatBuffer vertices) {
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        addVerticesToBuffer(bufferBuilder, guiGraphics.pose().last().pose(), vertices);
        postDraw(guiGraphics, bufferBuilder);
    }

    private void addVerticesToBuffer(BufferBuilder bufferBuilder, Matrix4f matrix4f, FloatBuffer vertices) {
        vertices.rewind();
        while(vertices.hasRemaining()) {
            bufferBuilder.vertex(matrix4f, vertices.get(), vertices.get(), this.getZLevel()).color(1f,1f,1f,1f).endVertex();
        }
    }
}
