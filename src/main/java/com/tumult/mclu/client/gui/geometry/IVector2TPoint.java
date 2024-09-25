package com.tumult.mclu.client.gui.geometry;

public interface IVector2TPoint<T extends Number> {
    T getX();
    T getY();
    void setX(T x);
    void setY(T y);

    IVector2TPoint<T> add(IVector2TPoint<T> point);
    IVector2TPoint<T> subtract(IVector2TPoint<T> point);
    IVector2TPoint<T> multiply(T factor);
    IVector2TPoint<T> divide(T factor);

    IVector2TPoint<T> interpolate(IVector2TPoint<T> target, double factor);
}
