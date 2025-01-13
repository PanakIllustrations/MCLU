package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {
    protected Node parent = null;
    protected List<Node> children = new ArrayList<>();

    public Vector4DRect visibilityBounds;
    public Vector4DRect rectBounds;
    protected Color shade = null;
    protected float zLevel = 1.0f;
    protected boolean isVisible = false;

    public Node (Vector4DRect rectBounds, Vector4DRect visibilityBounds) {
        this.rectBounds = rectBounds;
        this.visibilityBounds = visibilityBounds;
    }

    public Node (Vector4DRect rectBounds) {
        this.rectBounds = rectBounds;
        this.visibilityBounds = rectBounds;
    }

    public void setUL(Vector2DPoint ul) {
        this.rectBounds.setUl(ul);
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
    }
}
