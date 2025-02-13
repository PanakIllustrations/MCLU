package com.tumult.mclu.client.gui.frame.old;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public String name = "";
    public Node parent = this;
    public List<Node> children = new ArrayList<>();

    public void addChild(Node child) {
        children.add(child);
        child.parent = this;
    }
    public void removeChild(Node child) {
        children.remove(child);
        child.parent = child;
    }
    public void removeChildren() {
        children.clear();
    }
    public void addChildren(List<Node> children) {
        this.children.addAll(children);
        this.children.forEach(child -> child.parent = this);
    }
}
