package com.tumult.mclu.client.gui.geometry;

public class Rectangle {
    Vector2DPoint nwCorner;
    Vector2DPoint whSides;
    protected Rectangle rightOfThis;
    protected Rectangle leftOfThis;
    protected Rectangle aboveThis;
    protected Rectangle belowThis;

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
    public Rectangle getRightOfThis() {
        return rightOfThis;
    }
    public Rectangle getLeftOfThis() {
        return leftOfThis;
    }
    public Rectangle getBelowThis() {
        return belowThis;
    }
    public Rectangle getAboveThis() {
        return aboveThis;
    }

    // setters
    public void setNwCorner(double x, double y) {
        this.nwCorner.setX(x);
        this.nwCorner.setY(y);
    }
    public void setNwCorner(Vector2DPoint nwCorner) {
        this.nwCorner = nwCorner;
    }
    public void setWhSides(double w, double h) {
        this.whSides.setX(w);
        this.whSides.setY(h);
    }
    public void setWhSides(Vector2DPoint whSides) {
        this.whSides = whSides;
    }
    public void setLeft(double left) {
        this.nwCorner.setX(left);
    }
    public void setRight(double right) {
        this.nwCorner.setX(right);
    }
    public void setWidth(double width) {
        this.whSides.setX(width);
    }
    public void setHeight(double height) {
        this.whSides.setY(height);
    }

    public void setRightOfThis(Rectangle rightOfThis) {
        this.rightOfThis = rightOfThis;
    }
    public void setLeftOfThis(Rectangle leftOfThis) {
        this.leftOfThis = leftOfThis;
    }
    public void setAboveThis(Rectangle aboveThis) {
        this.aboveThis = aboveThis;
    }
    public void setBelowThis(Rectangle belowThis) {
        this.belowThis = belowThis;
    }

    // utility
    public void moveBy(double x, double y) {
        this.moveBy(new Vector2DPoint(x, y));
    }
    public void moveBy(Vector2DPoint amount) {
        setNwCorner(getNwCorner().add(amount));
    }

}
