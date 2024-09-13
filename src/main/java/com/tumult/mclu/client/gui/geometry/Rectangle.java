package com.tumult.mclu.client.gui.geometry;

public class Rectangle implements IRectangle {
    Vector2DPoint nwCorner;
    Vector2DPoint whSides;
    public Rectangle(double left, double top, double right, double bottom) {
        this(left, top, right, bottom, false);
    }
    public Rectangle(double left, double top, double right, double bottom, boolean growFromCenter) {
        this(new Vector2DPoint(left, top), new Vector2DPoint(right, bottom), growFromCenter);
    }
    public Rectangle(Vector2DPoint nwCorner, Vector2DPoint seCorner) {
        this(nwCorner, seCorner, false);
    }
    public Rectangle(Vector2DPoint nwCorner, Vector2DPoint seCorner, boolean growFromCenter) {
        this.nwCorner = nwCorner;
        this.whSides = seCorner.subtract(nwCorner);
        if (growFromCenter) {
            Vector2DPoint center = nwCorner.add(whSides.multiply(0.5));
            //this.nwCorner = new Vector2DPoint(nwCorner);
            //this.whSides = new Vector2DPoint(center);
        }
    }
}
