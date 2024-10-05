package com.tumult.mclu.client.gui.frame.geometry;

import java.util.ArrayList;
import java.util.List;

public class Vector4DRect {
    public Vector2DPoint ul;
    public Vector2DPoint wh;
    // Constructors
    public Vector4DRect(double x, double y, double w, double h) {
        this.ul = new Vector2DPoint(x, y);
        this.wh = new Vector2DPoint(w, h);
    }
    public Vector4DRect(Vector2DPoint ul, Vector2DPoint wh) {
        this.ul = ul;
        this.wh = wh;
    }
    public Vector4DRect(Vector4DRect r) {
        this.ul = r.ul;
        this.wh = r.wh;
    }
    public Vector4DRect copy(){
        return new Vector4DRect(ul, wh);
    }
    // Getters
    public Vector2DPoint getUl() {
        return ul;
    }
    public Vector2DPoint getWh() {
        return wh;
    }
    public Vector2DPoint getBr() {
        return ul.add(wh);
    }
    public Vector2DPoint getCenter(){
        return ul.mid(getBr());
    }
    public double left() {
        return ul.x();
    }
    public double right() {
        return getBr().x();
    }
    public double top() {
        return ul.y();
    }
    public double bottom() {
        return getBr().y();
    }
    public double width() {
        return wh.x();
    }
    public double height() {
        return wh.y();
    }
    // Setters
    public void setUl(Vector2DPoint ul) {
        this.ul = ul;
    }
    public void setUl(double x, double y) {this.ul.set(x, y);}
    public void setWh(Vector2DPoint wh) {
        this.wh = wh;
    }
    public void setWh(double x, double y) {this.wh.set(x, y);}
    public void setCenter(Vector2DPoint p){
        this.ul = p.sub(wh.div(2, 2));
    }
    // arithmetic
    public Vector4DRect normalize(Vector2DPoint scalar) {
        return new Vector4DRect(ul.div(scalar), wh.div(scalar));
    }
    // geographic
    public boolean contains(Vector2DPoint v) {
        return v.x() > left() && v.x() < right() && v.y() > top() && v.y() < bottom();
    }
}
