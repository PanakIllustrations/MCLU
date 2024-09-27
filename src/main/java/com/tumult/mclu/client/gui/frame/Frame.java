package com.tumult.mclu.client.gui.frame;

import com.tumult.mclu.client.gui.geometry.Vector2DPoint;
import com.tumult.mclu.client.gui.geometry.Vector4DRect;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    private Vector4DRect boundingRect;
    private List<GuiComponent> children = null;

    public Frame(double x, double y, double w, double h) {
        this.boundingRect = new Vector4DRect(x, y, w, h);
        this.children = new ArrayList<>();
    }
    public Frame(Vector4DRect boundingBox) {
        this.boundingRect = boundingBox;
        this.children = new ArrayList<>();
    }
    public void addChild(GuiComponent child) {
        children.add(child.setParent(this));
    }
    public List<GuiComponent> getChildren() {
        return children;
    }
    public void translate(double x, double y) {
        boundingRect.setUl(boundingRect.getUl().add(x, y));
        for (Vector4DRect child : children) {
            child.setUl(child.getUl().add(x, y));
        }
    }
    public void scale(double x, double y) {
        boundingRect.setWh(boundingRect.getWh().add(x, y));
        for (Vector4DRect child : children) {
            child.setWh(child.getWh().add(x, y));
        }
    }
    public void translateParent(double x, double y) {
        boundingRect.setUl(boundingRect.getUl().add(x, y));
    }
    public void scaleParent(double x, double y) {
        boundingRect.setWh(boundingRect.getWh().add(x, y));
    }
    public void setBoundingRect(double x, double y, double w, double h) {
        boundingRect = new Vector4DRect(x, y, w, h);
    }
    public void setBoundingRect(Vector4DRect r) {
        boundingRect = r;
    }
    public Vector4DRect getBoundingRect() {
        return boundingRect;
    }
}
