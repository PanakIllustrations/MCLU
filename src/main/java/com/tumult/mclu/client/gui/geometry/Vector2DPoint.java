package com.tumult.mclu.client.gui.geometry;

public class Vector2DPoint implements IVector2TPoint<Double> {
    private double x;
    private double y;

    public Vector2DPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public void setX(Double x) {
        this.x = x;
    }

    @Override
    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public IVector2TPoint<Double> add(IVector2TPoint<Double> point) {
        return new Vector2DPoint(x + point.getX(), y + point.getY());
    }

    @Override
    public IVector2TPoint<Double> subtract(IVector2TPoint<Double> point) {
        return new Vector2DPoint(x - point.getX(), y - point.getY());
    }

    @Override
    public IVector2TPoint<Double> multiply(Double factor) {
        return new Vector2DPoint(x * factor, y * factor);
    }

    @Override
    public IVector2TPoint<Double> divide(Double factor) {
        return new Vector2DPoint(x / factor, y / factor);
    }

    @Override
    public IVector2TPoint<Double> interpolate(IVector2TPoint<Double> target, double factor) {
        return new Vector2DPoint((this.getX() + target.getX()) / factor, (this.getY() + target.getY()) / factor);
    }

    public IVector2TPoint<Double> normalize() {
        double magnitude = Math.sqrt(x * x + y * y);
        return new Vector2DPoint(x / magnitude, y / magnitude);
    }
}