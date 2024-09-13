package com.tumult.mclu.client.gui.geometry;

public interface IRectangle
{
    double nEdge();
    double sEdge();
    double eEdge();
    double wEdge();

    double width();
    double height();

    IRectangle setNorthEdge(double value);
    IRectangle setSouthEdge(double value);
    IRectangle setEastEdge(double value);
    IRectangle setWestEdge(double value);

    IRectangle setWidth(double value);
    IRectangle setHeight(double value);

    default boolean contains(double x, double y) {
        return x > wEdge() && x < eEdge()
            && y > nEdge() && y < sEdge();
    }
}
