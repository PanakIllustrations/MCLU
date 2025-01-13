package com.tumult.mclu.client.gui.frame.core;

import com.tumult.mclu.client.gui.frame.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.frame.geometry.Vector4DRect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {
    protected Node parent = null;
    protected List<Node> children = new ArrayList<>();

    protected Color shade = null;
    protected float zLevel = 1.0f;
    protected boolean isVisible = true;

    public void toggleVisibility() {
        isVisible = !isVisible;
    }
}
