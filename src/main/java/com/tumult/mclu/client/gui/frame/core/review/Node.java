package com.tumult.mclu.client.gui.frame.core.review;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.tumult.mclu.client.gui.frame.core.geometry.IRect;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class Node extends GuiIcon {
    public Node parent;
    public List<Node> children = new ArrayList<>();

    public Node(IRect rect, Color color, @Nullable Node parent) {
        super(rect, color);
        this.parent = parent;
    }

    public Node(IRect rect, String name, int size, Color color, @Nullable Node parent) {
        super(rect, name, size, color);
        this.parent = parent;
    }

    public void addChild(Node child) {
        children.add(child);
        child.parent = this;
    }

    public void removeChild(Node child) {
        children.remove(child);
        child.parent = null; // Corrected from `child.parent = child;`
    }

    public void removeChildren() {
        for (Node child : children) {
            child.parent = null;
        }
        children.clear();
    }

    public void addChildren(List<Node> children) {
        this.children.addAll(children);
        for (Node child : children) {
            child.parent = this;
        }
    }

    public void render() {
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        for (Node child : children) {
            if (child.parent == this) {
                child.render(); // Corrected recursive call
            }
            child.render(builder);
        }
    }
}
