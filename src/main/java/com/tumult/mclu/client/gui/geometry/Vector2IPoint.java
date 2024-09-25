package com.tumult.mclu.client.gui.geometry;

public class Vector2IPoint implements IVector2TPoint<Integer> {
    private int x;
    private int y;

    public Vector2IPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Integer getX() {
        return x;
    }
    @Override
    public Integer getY() {
        return y;
    }
    @Override
    public void setX(Integer x) {
        this.x = x;
    }
    @Override
    public void setY(Integer y) {
        this.y = y;
    }


    @Override
    public IVector2TPoint<Integer> add(IVector2TPoint<Integer> point) {
        return new Vector2IPoint(x + point.getX(), y + point.getY());
    }

    @Override
    public IVector2TPoint<Integer> subtract(IVector2TPoint<Integer> point) {
        return new Vector2IPoint(x - point.getX(), y - point.getY());
    }

    @Override
    public IVector2TPoint<Integer> multiply(Integer factor) {
        return new Vector2IPoint(x * factor, y * factor);
    }

    @Override
    public IVector2TPoint<Integer> divide(Integer factor) {
        return new Vector2IPoint(x / factor, y / factor);
    }
    @Override
    public IVector2TPoint<Integer> interpolate(IVector2TPoint<Integer> target, double factor) {
        return new Vector2IPoint((int)((this.getX() + target.getX()) / factor), (int)((this.getY() + target.getY()) / factor));
    }

}
