package com.tumult.mclu.client.gui.geometry;

public class Vector2DPoint {
    public double x;
    public double y;

    // constructors
    public Vector2DPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2DPoint(Vector2DPoint v) {
        this(v.x, v.y);
    }
    // getters
    public double x() {
        return x;
    }
    public double y() {
        return y;
    }
    // setters
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    // arithmetic
    public Vector2DPoint add(double x, double y) {
        return new Vector2DPoint(x() + x, y() + y);
    }
    public Vector2DPoint sub(double x, double y) {
        return new Vector2DPoint(x() - x, y() - y);
    }
    public Vector2DPoint mul(double x, double y) {
        return new Vector2DPoint(x() * x, y() * y);
    }
    public Vector2DPoint div(double x, double y) {
        return new Vector2DPoint(x() / x, y() / y);
    }

    public Vector2DPoint sub(Vector2DPoint v) {
        return new Vector2DPoint(x() - v.x(), y() - v.y());
    }
    public Vector2DPoint add(Vector2DPoint v) {
        return new Vector2DPoint(x() + v.x(), y() + v.y());
    }
    public Vector2DPoint mul(Vector2DPoint v) {
        return new Vector2DPoint(x() * v.x(), y() * v.y());
    }
    public Vector2DPoint div(Vector2DPoint v) {
        return new Vector2DPoint(x() / v.x(), y() / v.y());
    }
    // geographic
    public Vector2DPoint mid(Vector2DPoint v) {
        return new Vector2DPoint((this.x() + v.x()) / 2, (this.y() + v.y()) / 2);
    }
}
