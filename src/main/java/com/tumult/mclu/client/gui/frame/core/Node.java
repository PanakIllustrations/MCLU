package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    protected Node parent = null;
    protected List<Node> children = new ArrayList<>();

    protected Color shade = null;
    protected float zLevel = 1.0f;
    protected boolean isVisible = true;

    // Abstract method for bounds - all UI elements need bounds for hit testing
    public abstract Vector4DRect getBounds();

    // Abstract draw method
    public abstract void draw(GuiGraphics guiGraphics);

    // Draw this node and all children
    public void drawTree(GuiGraphics guiGraphics) {
        if (!isVisible) return;

        draw(guiGraphics);
        for (Node child : children) {
            child.drawTree(guiGraphics);
        }
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(Node child) {
        children.remove(child);
        child.setParent(null);
    }

    // Useful for hit testing throughout the tree
    public Node findNodeAt(Vector2DPoint point) {
        if (!isVisible) return null;

        // Check children first (they're on top)
        for (int i = children.size() - 1; i >= 0; i--) {
            Node found = children.get(i).findNodeAt(point);
            if (found != null) return found;
        }

        // Then check this node
        if (getBounds().contains(point)) {
            return this;
        }

        return null;
    }
}
