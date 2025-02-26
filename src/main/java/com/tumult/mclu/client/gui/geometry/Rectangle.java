package com.tumult.mclu.client.gui.geometry;


import org.joml.Vector4d;
import org.joml.Vector2d;

public class Rectangle extends Vector4d {
    protected Rectangle rightOfThis;
    protected Rectangle leftOfThis;
    protected Rectangle aboveThis;
    protected Rectangle belowThis;
    protected Vector4d margin = new Vector4d().zero();

    public boolean isMoving = false;

    public Rectangle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }
    public Rectangle(Vector2d nwCorner, Vector2d whSides) {
        this(nwCorner.x, nwCorner.y, whSides.x, whSides.y);
    }
    public Rectangle(Vector2d whSides) {
        this(0, 0, whSides.x, whSides.y);
    }

    public double getLeft() {
        if (leftOfThis != null && !isMoving) {
            // Update this rectangle's x position based on the left neighbor's right edge and the margin
            this.x = leftOfThis.getLeft()
                    + leftOfThis.getWidth()  // Right edge of the left neighbor
                    + Math.max(leftOfThis.getRightMargin(), this.getLeftMargin());
        }
        return this.x;
    }

    public double getRight() {
        double right = this.x + this.getWidth();  // This rectangle's right edge
        if (rightOfThis != null && !isMoving) {
            // Update the right edge if necessary
            double requiredRightEdge = rightOfThis.x - rightOfThis.getLeftMargin();
            if (right > requiredRightEdge) {
                // Adjust this rectangle's position to fit within the margins
                this.x = rightOfThis.x - rightOfThis.getLeftMargin() - this.getWidth();
                right = this.x + this.getWidth();  // Recalculate the right edge
            }
        }
        return right;
    }

    public double getTop() {
        if (aboveThis != null && !isMoving) {
            // Update this rectangle's y position based on the top neighbor's bottom edge and margin
            this.y = aboveThis.getTop()
                    + aboveThis.getHeight()  // Bottom edge of the top neighbor
                    + Math.max(aboveThis.getBottomMargin(), this.getTopMargin());
        }
        return this.y;
    }

    public double getBottom() {
        double bottom = this.y + this.getHeight();  // Calculate this rectangle's bottom edge
        if (belowThis != null && !isMoving) {
            // Set this rectangle as the top neighbor of the one below
            // Update the bottom edge if necessary
            double requiredBottomEdge = belowThis.y - belowThis.getTopMargin();
            if (bottom > requiredBottomEdge) {
                // Adjust this rectangle's position to fit within the margins
                this.y = belowThis.y - belowThis.getTopMargin() - this.getHeight();
                bottom = this.y + this.getHeight();  // Recalculate the bottom edge
            }
        }
        return bottom;
    }

    public void setRightOfThis(Rectangle r) {
        this.rightOfThis = r;
    }
    public void setLeftOfThis(Rectangle r) {
        this.leftOfThis = r;
    }
    public void setAboveThis(Rectangle r) {
        this.aboveThis = r;
    }
    public void setBelowThis(Rectangle r) {
        this.belowThis = r;
    }

    public void moveTo(Vector2d d){
        this.x = d.x;
        this.y = d.y;
    }
    public void moveBy(Vector2d d){
        this.x += d.x;
        this.y += d.y;
    }

    // getters for clarification
    public double getWidth(){
        return z;
    }
    public double getHeight(){
        return w;
    }
    public void setWidth(double width) {
        this.z = width;
    }
    public void setHeight(double height) {
        this.w = height;
    }
    public double getLeftMargin(){
        return margin.x;
    }
    public double getTopMargin(){
        return margin.y;
    }
    public double getRightMargin(){
        return margin.z;
    }
    public double getBottomMargin(){
        return margin.w;
    }

    public Vector2d getNwCorner(){
        return new Vector2d(this.x,this.y);
    }
    public Vector2d getWhSides(){
        return new Vector2d(this.getWidth(),this.getHeight());
    }

    public Rectangle divide(Vector2d scalar) {
        return new Rectangle(getNwCorner().div(scalar), getWhSides().div(scalar));
    }

}
