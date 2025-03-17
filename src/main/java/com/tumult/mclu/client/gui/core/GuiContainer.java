package com.tumult.mclu.client.gui.core;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.tumult.mclu.client.gui.core.geometry.Rect;
import com.tumult.mclu.client.gui.core.geometry.RoundRect;

import com.tumult.mclu.client.gui.core.util.BufferProvider;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


import com.tumult.mclu.client.gui.core.util.UIProfiler;

public class GuiContainer { // extends AbstractGuiElement<Rect> { //implements IContainer {
//    private final List<AbstractGuiElement<?>> children = new ArrayList<>();
//    private LayoutStrategy layoutStrategy;
//    private boolean layoutDirty = true;
//    private final Color backgroundColor;
//
//    public GuiContainer(RoundRect rect, Color backgroundColor) {
//        super(rect);
//        this.backgroundColor = backgroundColor;
//        format = com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR;
//        color = new float[]{
//                backgroundColor.getRed() / 255f,
//                backgroundColor.getGreen() / 255f,
//                backgroundColor.getBlue() / 255f,
//                backgroundColor.getAlpha() / 255f
//        };
//    }
//
//    // IContainer implementation
//    @Override
//    public List<AbstractGuiElement<?>> getChildren() {
//        return children;
//    }
//
//    @Override
//    public void addChild(AbstractGuiElement<?> child) {
//        children.add(child);
//        child.rect.setParent(this);
//        markLayoutDirty();
//    }
//
//    @Override
//    public void removeChild(AbstractGuiElement<?> child) {
//        if (children.remove(child)) {
//            child.rect.setParent(null);
//            markLayoutDirty();
//        }
//    }
//
//    @Override
//    public LayoutStrategy getLayoutStrategy() {
//        return layoutStrategy;
//    }
//
//    @Override
//    public void setLayoutStrategy(LayoutStrategy strategy) {
//        this.layoutStrategy = strategy;
//        markLayoutDirty();
//    }
//
//    @Override
//    public void layoutChildren() {
//        UIProfiler.start("layout_" + id);
//        if (layoutDirty && layoutStrategy != null) {
//            layoutStrategy.applyLayout(this);
//            layoutDirty = false;
//        }
//        UIProfiler.end("layout_" + id);
//    }
//
//    @Override
//    public boolean isLayoutDirty() {
//        return layoutDirty;
//    }
//
//    @Override
//    public void markLayoutDirty() {
//        layoutDirty = true;
//    }
//
//    @Override
//    public void clearLayoutDirty() {
//        layoutDirty = false;
//    }
//
//    // IRect delegation methods - these are the missing implementations
//    @Override
//    public float left() {
//        return rect.left();
//    }
//
//    @Override
//    public float top() {
//        return rect.top();
//    }
//
//    @Override
//    public float width() {
//        return rect.width();
//    }
//
//    @Override
//    public float height() {
//        return rect.height();
//    }
//
//    @Override
//    public float depth() {
//        return rect.depth();
//    }
//
//    @Override
//    public IContainer getParent() {
//        return (IContainer) rect.getParent();
//    }
//
//    @Override
//    public void setParent(IContainer parent) {
//        rect.setParent(parent);
//    }
//
//    @Override
//    public int resolution() {
//        return rect.resolution();
//    }
//
//    @Override
//    public com.mojang.blaze3d.vertex.VertexFormat.Mode mode() {
//        return rect.mode();
//    }
//
//    @Override
//    public void setUL(float x, float y) {
//        rect.setUL(x, y);
//    }
//
//    @Override
//    public void setWH(float w, float h) {
//        rect.setWH(w, h);
//    }
//
//    @Override
//    public void setBR(float b, float r) {
//        rect.setBR(b, r);
//    }
//
//    @Override
//    public void setDepth(float zLevel) {
//        rect.setDepth(zLevel);
//    }
//
//    @Override
//    public void copyTo(float[] out) {
//        rect.copyTo(out);
//    }
//
//    @Override
//    public boolean isDirty() {
//        return rect.isDirty();
//    }
//
//    @Override
//    public void markDirty() {
//        rect.markDirty();
//    }
//
//    @Override
//    public void clearDirty() {
//        rect.clearDirty();
//    }
//
//    // Rendering methods
//    @Override
//    protected void doRender() {
//        // First layout children if needed
//        layoutChildren();
//
//        // Render this container's background
//        ShaderInstance oldShader = RenderSystem.getShader();
//        updateShaderInstance();
//        BufferProvider.drawPointsCol(rect, rect.mode(), format, points, color, rect.depth());
//        RenderSystem.setShader(() -> oldShader);
//
//        // Then render all children
//        for (AbstractGuiElement<?> child : children) {
//            child.render();
//        }
//    }
//
//    @Override
//    void updateShaderInstance() {
//        RenderSystem.setShader(GameRenderer::getPositionColorShader);
//        RenderSystem.setShaderColor(color[0], color[1], color[2], color[3]);
//    }
//
//    @Override
//    public void addToBuffer(BufferBuilder builder) {
//        float x = rect.getAbsoluteLeft();
//        float y = rect.getAbsoluteTop();
//
//        for (int i = 0; i < points.length; i += 2){
//            builder
//                    .vertex(points[i] + x, points[i+1] + y, rect.depth())
//                    .color(color[0], color[1], color[2], color[3])
//                    .endVertex();
//        }
//    }
//
//    @Override
//    public void handleDragging(float[] pos, boolean[] buttons) {
//        super.handleDragging(pos, buttons);
//
//        // Allow child elements to be dragged independently
//        for (AbstractGuiElement<?> child : children) {
//            child.handleDragging(pos, buttons);
//        }
//    }
}