package com.tumult.mclu.client.gui.geometry;

public class Vector2DPoint {
    public double x;
    public double y;

    public Vector2DPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector2DPoint(Vector2DPoint point) {
        this(point.x, point.y);
    }
    double getX(){
        return x;
    }
    double getY(){
        return y;
    }

    void setX(double x) {
        this.x = x;
    }
    void setY(double y) {
        this.y = y;
    }

    public Vector2DPoint add(Vector2DPoint point) {
        return new Vector2DPoint(x + point.x, y + point.y);
    }
    public Vector2DPoint subtract(Vector2DPoint point) {
        return new Vector2DPoint(x - point.x, y - point.y);
    }
    public Vector2DPoint multiply(double factor) {
        return new Vector2DPoint(x * factor, y * factor);
    }
    public Vector2DPoint divide(double factor) {
        return new Vector2DPoint(x / factor, y / factor);
    }
    public Vector2DPoint normalize() {
        return new Vector2DPoint(x / x, y / y);
    }
    public Vector2DPoint clone() {
        return new Vector2DPoint(x * x, y * y);
    }
    public Vector2DPoint interpolate(Vector2DPoint target, double factor) {
        return new Vector2DPoint((this.getX() + target.getX()) / factor, (this.getY() + target.getY()) / factor);
    }
}
