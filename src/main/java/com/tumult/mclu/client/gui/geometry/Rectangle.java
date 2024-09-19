package com.tumult.mclu.client.gui.geometry;

public class Rectangle {
    Vector2DPoint nwCorner;
    Vector2DPoint whSides;

    public Rectangle(double left, double top, double right, double bottom) {
        this(new Vector2DPoint(left, top), new Vector2DPoint(right, bottom));
    }
    public Rectangle(Vector2DPoint nwCorner, Vector2DPoint whSides) {
        this.nwCorner = nwCorner;
        this.whSides = whSides;
    }

    // getters
    public double getTop() {
        return nwCorner.getY();
    }
    public double getBottom() {
        return getTop() + whSides.getY();
    }
    public double getLeft() {
        return nwCorner.getX();
    }
    public double getRight() {
        return getLeft() + whSides.getX();
    }
    public double getWidth() {
        return whSides.getX();
    }
    public double getHeight() {
        return whSides.getY();
    }
    public Vector2DPoint getNwCorner() {
        return nwCorner;
    }
    public Vector2DPoint getWhSides() {
        return whSides;
    }

    // setters
    public Rectangle setNwCorner(double x, double y) {
        this.nwCorner.setX(x);
        this.nwCorner.setY(y);
        return this;
    }
    public Rectangle setNwCorner(Vector2DPoint nwCorner) {
        this.nwCorner = nwCorner;
        return this;
    }
    public Rectangle setWhSides(double w, double h) {
        this.whSides.setX(w);
        this.whSides.setY(h);
        return this;
    }
    public Rectangle setWhSides(Vector2DPoint whSides) {
        this.whSides = whSides;
        return this;
    }
    public Rectangle setLeft(double left) {
        this.nwCorner.setX(left);
        return this;
    }
    public Rectangle setRight(double right) {
        this.nwCorner.setX(right);
        return this;
    }
    public Rectangle setWidth(double width) {
        this.whSides.setX(width);
        return this;
    }
    public Rectangle setHeight(double height) {
        this.whSides.setY(height);
        return this;
    }

    // utility
    public void moveBy(double x, double y) {
        this.moveBy(new Vector2DPoint(x, y));
    }
    public void moveBy(Vector2DPoint amount) {
        setNwCorner(getNwCorner().add(amount));
    }

}
